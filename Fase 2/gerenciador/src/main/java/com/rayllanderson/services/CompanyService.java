package com.rayllanderson.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rayllanderson.entities.Company;

public class CompanyService {
	
	private static List<Company> companies = new ArrayList<>();
	private static Integer sequenceId = 1;
	
	static {
		companies.addAll(Arrays.asList(new Company(sequenceId ++, "Google"), new Company(sequenceId ++, "Amazon")));
	}
	
	public void register (Company company) {
		company.setId(sequenceId++);
		companies.add(company);
	}
	
	public void deleteById (int id) {
		companies.removeIf(x -> x.getId() == id);
	}
	
	public List<Company> findAll() {
		return companies;
	}

}
