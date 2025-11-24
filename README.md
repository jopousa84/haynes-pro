#Problem Description

This Identification Service is a REST service that returns make, 
model and type information from our database. The code has been
provided and as you can see all requests directly cause a database 
query to be executed. 

In the past this has served us well: Typical usage was that an 
end-user would stick with a certain selection for some time. And the 
well behaving J2EE applications that we build for these end-users
made proper use of sessions and/or caching. So they never flooded
this identification server with a lot of requests.

Nowadays this is different, first we started with number-plate 
lookup services that also use this Identification Service. These
services became very popular and cause seemingly random requests
to our identification data. And these are many. 

And besides that we've opened the Identification Service to third
party clients as well, and that caused unexpected usage of the
service. Although we charge per request (bundle) these JS/Python/PHP
clients appear not to do much caching on their side. We sometimes 
see a client doing exactly these same request about 20/30 times in
a couple of minutes. And strangely enough they never complain 
about the high costs directly, but they comment on the 
services being slow at times, which they don't expect this at this
price-point.

In short: the database cannot keep up with all these requests and
something needs to happen. 

#Solutions needed

We need to review the operational aspect of this application and
are interested too see if you have some ideas on this matter.
We'd appreciate it is if you can put (at leasts one) of these ideas
in practice and show us in code how this actually could work.

You are allowed to use whatever you see fit. If you need additional
tools and services you can run them locally our you can use 
AWS Free Tier that is all up to you.


IMPORTANT: To start the Redis Instance, you can use the docker-compose.yml file provided in the project.

Main measures to improve performance
------------------------------------
+ Introduce server-side caching so repeated requests don’t hit the database. This is the most important improvement,
  since the main bottleneck was caused by excessive repeated queries.
+ Reduce database load by optimizing queries and indexes: I modified the repository/entity configuration for the Model
  and Type tables to make them more efficient, added some indexes, and corrected the DB relationship 
  (changed OneToOne to ManyToOne in Model & Type).
+ Scale the database horizontally using read replicas to absorb remaining read traffic. This isn’t reflected in the
  code since we’re using an H2 in-memory database, but it’s something I would implement in a real production environment.

Notes regarding server-side caching
-----------------------------------
+ I assume the data does not need to be instantly refreshed after being loaded into the system. That’s why I set 
  Redis to refresh every hour, although this can be adjusted in application.properties. Since this information 
  doesn’t change frequently, a longer-lived cache (more than 1 hour) could also be appropriate.

+ Another option would have been to use Spring Cache backed by Caffeine, but in a production environment with multiple
  instances a Redis-based cache is generally more efficient because it is shared across nodes.

+ Alternative for caching Makes: Since the Makes table is small, another option is to load the entire table into memory
  per instance. This data changes rarely, so a nightly update with a cron job could work as well.

+ I didn’t add @Cacheable for getTypeById, getMakeById, or getModelById because I assumed these endpoints would be less
  frequently used. The most common flows should be:
  - Get all Makes → get the Models for one Make → get the Types for that Model
  - Get Models by MakeId → get the Types for that Model
  That said, those endpoints can also be cached if needed.

General notes
-------------
+ I didn’t modify the endpoints themselves, assuming the requirement was to keep the existing API and simply improve how
  they interact with the database. If response structures could be changed, I would optimize them—for example,
  getModelsByMakeId always returns the Make inside each Model, which is redundant.

+ I focused only on the assessment requirements. I didn’t modify other areas that could be improved (e.g., handling 
  NotFoundException when searching by ID). I can add these if needed, but understood they were outside the scope.