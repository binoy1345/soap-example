package hello;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.Countries;
import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.CountryAsRequest;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	@Autowired
	private CountryRepository countryRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		CountryDao countryDao=countryRepository.findCountryByName(request.getName()).get();
		response.setCountry(getValuesFromDao(countryDao));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCountryRequest")
	@ResponsePayload
	public GetCountryResponse updateCountry(@RequestPayload CountryAsRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		Country country = request.getCountry();
		countryRepository.save(setValuesToDao(country));
		response.setCountry(country);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCountriesRequest")
	@ResponsePayload
	public void updateCountries(@RequestPayload Countries request) {
		List<Country> countries = request.getCountries();
		countries.stream().forEach(country->{
			countryRepository.save(setValuesToDao(country));
		});
	}
	
	private CountryDao setValuesToDao(Country country) {
		CountryDao countryDao = new CountryDao();
		countryDao.setName(country.getName());
		countryDao.setPopulation(country.getPopulation());
		countryDao.setCapital(country.getCapital());
		countryDao.setCurrency(country.getCurrency());
		return countryDao;
	}
	
	private Country getValuesFromDao(CountryDao countryTao) {
		Country country = new Country();
		country.setName(countryTao.getName());
		country.setPopulation(countryTao.getPopulation());
		country.setCapital(countryTao.getCapital());
		country.setCurrency(countryTao.getCurrency());
		return country;
	}
	
}