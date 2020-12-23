package io.bankbridge.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankModel implements Serializable {
	
	public String bic;
	public String name;
	public String countryCode;
	public String auth;
	public ArrayList products;

	public BankModel() {
	}

	public BankModel(String bic, String name, String countryCode, ArrayList products) {
		this.bic = bic;
		this.name = name;
		this.countryCode = countryCode;
		this.products = products;
	}

	public String getBic() {
		return bic;
	}

	public String getName() {
		return name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getAuth() {
		return auth;
	}

	public ArrayList getProducts() {
		return products;
	}
}
