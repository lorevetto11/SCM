package com.gpi.scm.interceptors;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.utils.UserContextHolder;

public class StandardInfoLoggingInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -1316876880658639295L;
	private static final Logger logger = Logger.getLogger(StandardInfoLoggingInterceptor.class);

	// This method is called when an object gets updated.
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

		// applico solo su entit� del modulo FormConfigurator
		if (entity instanceof GenericEntity) {

			logger.debug("onFlushDirty " + entity.getClass().getName() + " [user: " + UserContextHolder.getUser().getUsername() + "]");
			boolean updated = false;

			// ciclo sui valori attuali
			for (int i = 0; i < currentState.length; i++) {

				// verifico se il valore � stato modificato
				if (currentState[i] == null) {
					if (previousState[i] != null) {
						updated = true;
					}
				} else if (!currentState[i].equals(previousState[i])) {
					updated = true;
				}

				// se il campo modificato � l'hibernate version non applico
				// nessun update
				if (GenericEntity.VERSION_FIELD.equals(propertyNames[i])) {
					updated = false;
				}

				// se il valore � stato modificato
				if (updated) {

					for (int j = 0; j < propertyNames.length; j++) {
						// aggiorno lastupdateDatetime
						if (GenericEntity.UPDATETIME_FIELD.equals(propertyNames[j])) {
							currentState[j] = new Date();
						}
						// aggiorno lastupdateUser
						else if (GenericEntity.UPDATEUSER_FIELD.equals(propertyNames[j])) {
							currentState[j] = UserContextHolder.getUser().getUsername();
						}
					}

					// inoltre se il campo modificato � deleted
					if (GenericEntity.DELETED_FIELD.equals(propertyNames[i])) {
						for (int j = 0; j < propertyNames.length; j++) {

							// valorizzo cancelDatetime o lo resetto a seconda
							// se TRUE o FALSE
							if (GenericEntity.DELETETIME_FIELD.equals(propertyNames[j])) {
								if (currentState[i] == Boolean.TRUE)
									currentState[j] = new Date();
								else
									currentState[j] = null;
							}

							// valorizzo lastupdateUser o lo resetto a seconda
							// se TRUE o FALSE
							else if (GenericEntity.DELETEUSER_FIELD.equals(propertyNames[j])) {
								if (currentState[i] == Boolean.TRUE)
									currentState[j] = UserContextHolder.getUser().getUsername();
								else
									currentState[j] = null;
							}
						}
					}
					updated = false;
				}
			}
			return true;
		}
		return false;
	}

	// This method is called when an object gets created.
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof GenericEntity) {
			logger.debug("onSave " + entity.getClass().getName() + " [user: " + UserContextHolder.getUser().getUsername() + "]");
			for (int i = 0; i < propertyNames.length; i++) {
				if (GenericEntity.VERSION_FIELD.equals(propertyNames[i])) {
					state[i] = 0L;
				} else if (GenericEntity.DELETED_FIELD.equals(propertyNames[i])) {
					state[i] = false;
				} else if (GenericEntity.INSERTTIME_FIELD.equals(propertyNames[i])) {
					state[i] = new Date();
				} else if (GenericEntity.INSERTUSER_FIELD.equals(propertyNames[i])) {
					state[i] = UserContextHolder.getUser().getUsername();
				}
			}
			return true;
		}
		return false;
	}

}
