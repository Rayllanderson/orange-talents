package com.rayllanderson.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rayllanderson.entities.Company;

public class CompanyService {
	
	private static List<Company> companies = new ArrayList<>();
	
	static {
		companies.addAll(Arrays.asList(new Company(null, "Google"), new Company(null, "Amazon")));
	}
	
	public void register (Company company) {
		companies.add(company);
	}
	
	public List<Company> findAll() {
		return companies;
	}

}
