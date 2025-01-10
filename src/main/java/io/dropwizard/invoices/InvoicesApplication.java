package io.dropwizard.invoices;

import org.dalesbred.Database;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.invoices.resources.InvoiceResources;

public class InvoicesApplication extends Application<InvoicesConfiguration> {

	public static void main(final String[] args) throws Exception {
		new InvoicesApplication().run(args);
	}

	@Override
	public String getName() {
		return "Invoices";
	}

	@Override
	public void initialize(final Bootstrap<InvoicesConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final InvoicesConfiguration configuration, final Environment environment) {
		DataSourceFactory config = configuration.getDataSourceFactory();
		final Database db = Database.forUrlAndCredentials(config.getUrl(), config.getUser(), config.getPassword());
		environment.jersey().register(new InvoiceResources(db));
	}

}
