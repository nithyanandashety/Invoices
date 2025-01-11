package io.dropwizard.invoices;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class InvoicesConfiguration extends Configuration {

	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;
	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory factory) {
		this.database = factory;
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;

	}
}
