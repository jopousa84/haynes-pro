package com.haynespro.assessment.mmt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.haynespro.assessment.mmt.repository.TypeRepository;
import com.haynespro.assessment.mmt.service.IdentificationService;

@SpringBootTest
public class AppTest{

	@Autowired TypeRepository typeRepository;

	@Autowired IdentificationService identificationService;

	@Test
    public void testApp() {
    	typeRepository.findAll().forEach(System.out::println);
    }
	
	@Test
    public void testMMT() {
		identificationService.getModelsByMakeId(
				identificationService.getAllMakes()
						.getFirst().getId())
								.forEach(System.out::println);

	identificationService.getTypesByModelId(
				identificationService.getModelsByMakeId(
						identificationService.getAllMakes()
								.getFirst().getId())
										.getFirst().getId())
												.forEach(System.out::println);
    }
	
}
