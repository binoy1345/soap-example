package org.bys;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.Countries;
import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.UpdateCountryRequest;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	@Autowired
	private CountryRepository countryRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		CountryEntity countryDao=countryRepository.findCountryByName(request.getName()).get();
		response.setCountry(getValuesFromDao(countryDao));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCountryRequest")
	@ResponsePayload
	public GetCountryResponse updateCountry(@RequestPayload UpdateCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		Country country = request.getCountry();
		countryRepository.save(setValuesToDao(country));
		response.setCountry(country);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCountriesRequest")
	@ResponsePayload
	public void updateCountries(@RequestPayload Countries request) {
		List<Country> countries = request.getCountry();
		countries.stream().forEach(country->{
			countryRepository.save(setValuesToDao(country));
		});
	}
	
	private CountryEntity setValuesToDao(Country country) {
		CountryEntity countryDao = new CountryEntity();
		countryDao.setName(country.getName());
		countryDao.setPopulation(country.getPopulation());
		countryDao.setCapital(country.getCapital());
		countryDao.setCurrency(country.getCurrency());
		return countryDao;
	}
	
	private Country getValuesFromDao(CountryEntity countryTao) {
		Country country = new Country();
		country.setName(countryTao.getName());
		country.setPopulation(countryTao.getPopulation());
		country.setCapital(countryTao.getCapital());
		country.setCurrency(countryTao.getCurrency());
		return country;
	}
	
}
