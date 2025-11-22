package com.haynespro.assessment.mmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haynespro.assessment.mmt.model.Make;
import com.haynespro.assessment.mmt.model.Model;
import com.haynespro.assessment.mmt.model.Type;
import com.haynespro.assessment.mmt.repository.MakeRepository;
import com.haynespro.assessment.mmt.repository.ModelRepository;
import com.haynespro.assessment.mmt.repository.TypeRepository;

@Service
public class IdentificationService {

	private final MakeRepository makeRepository;
	private final ModelRepository modelRepository;
	private final TypeRepository typeRepository;

	public IdentificationService(MakeRepository makeRepository, ModelRepository modelRepository,
			TypeRepository typeRepository) {
		this.makeRepository = makeRepository;
		this.modelRepository = modelRepository;
		this.typeRepository = typeRepository;
	}
	
	public List<Make> getAllMakes() {
		return makeRepository.findAll();
	}

	public Make getMakeById(int makeId) {
		return makeRepository.findById(makeId).get();
	}

	public List<Model> getModelsByMakeId(int makeId) {
		return modelRepository.findAllByMakeId(makeId);
	}
	
	public Model getModelById(int modelId) {
		return modelRepository.findById(modelId).get();
	}

	public List<Type> getTypesByModelId(int modelId) {
		return typeRepository.findAllByModelId(modelId);
	}

	public Type getTypeById(int typeId) {
		return typeRepository.findById(typeId).get();
	}

}
