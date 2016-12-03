package com.mikhailau.training.motordepot.dataaccess;

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;

public interface CredentialsDao extends AbstractDao<Credentials, Long> {

	Credentials find(String login, String password, CredentialsFilter filter);
	
	Credentials find(String login, CredentialsFilter filter);
}
