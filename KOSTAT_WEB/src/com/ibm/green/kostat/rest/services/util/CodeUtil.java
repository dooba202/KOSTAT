package com.ibm.green.kostat.rest.services.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.IndustryCodeDAO;
import com.ibm.green.kostat.daofactory.DAOFactory;
import com.ibm.green.kostat.dto.IndustryCodeDTO;
import com.ibm.green.kostat.enums.IndustryCodeType;
import com.ibm.green.kostat.exception.DataNotFoundException;
import com.ibm.green.kostat.exception.SerializationException;
import com.ibm.green.kostat.rest.json.JSONSerializer;
import com.ibm.green.kostat.rest.json.SelectionFilterEnum;

public class CodeUtil {
	
	static Log logger = LogFactory.getLog(CodeUtil.class);
	
	// Use Jackson JSON Serializer
	static JSONSerializer serializer;
	
	static String filterFieldsNoParent[];
	static String filterFieldsAll[];
	static Map<String, String> replaceMap;
	
	static {
		serializer = JSONSerializer.getSerializer(JSONSerializer.JACKSON); 
		
		filterFieldsNoParent  = new String[]{"id", "name"};
		filterFieldsAll = new String[]{"id", "name", "parent"};
	}
	
	
	public static String getIndustryCodeList(IndustryCodeType codeType) {
		
		DAOFactory daoFactory = null;
		
		try {
			daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLLOADER_DAO);
			daoFactory.beginTransaction();
			
			IndustryCodeDAO codeDAO = daoFactory.getIndustryCodeDAO();
			
			List<IndustryCodeDTO> codes = codeDAO.getCodeList(codeType);
		
			if ((codes == null) || (codes.size() == 0)) {
				logger.debug("Failed to find  code information : " + codeType); 
				throw new DataNotFoundException();
			}
			String filterFields[] = filterFieldsAll;
			if (codeType == IndustryCodeType.SanId) {
				filterFields = filterFieldsNoParent;
			} 

			String entity = serializer.serializeList(codes,  SelectionFilterEnum.INCLUDES, filterFields);	
			
			daoFactory.commitTransaction();			
			return entity;
			
		} catch (GreenRuntimeException ex) {

			if (daoFactory != null) daoFactory.rollbackTransaction();
			throw ex;
		} catch (SerializationException ex) {
			
			if (daoFactory != null) daoFactory.rollbackTransaction();
			logger.error("Exception during serialization codes for : " + codeType);
			throw new GreenRuntimeException(ex);
		} finally {
			
			if (daoFactory != null) daoFactory.endTransaction();			
		}
				
	}
}
