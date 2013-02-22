package com.ibm.green.kostat.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.kostat.dao.JisuDAO;
import com.ibm.green.kostat.daofactory.DAOFactory;
import com.ibm.green.kostat.dto.JisuDTO;
import com.ibm.green.kostat.enums.JisuType;
import com.ibm.green.kostat.rest.AbstractRESTResource;
import com.ibm.green.kostat.rest.ErrorMessage;
import com.ibm.green.kostat.rest.json.SelectionFilterEnum;

/**
 * JAX-RS Resource Class for Jisu Query Service
 */
@Path("/chart")
public class JisuService extends AbstractRESTResource {
	
	
	/**
	 * <pre>
	 * GET /chart/{jisuType}/{sanId}/{pumId}?from=yyyymm&to=yyyymm
	 * </pre>
	 * jisuType : jisu/jisuLM, jisuSM
	 * 
	 * @param sanId
	 * @param pumId
	 * @param fromYYYYMM
	 * @param toYYYYMM
	 * @return
	 */
	@GET
	@Path("{jisu}/{sanId}/{pumId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJisuListWithIndustryCode(@PathParam("jisu") String jisuStr, 
												@PathParam("sanId") String sanId, @PathParam("pumId") String pumId, 
												@QueryParam("from") String fromYYYYMM, @QueryParam("to") String toYYYYMM ) {	
		
		final String CUR_METHOD = "JisuService#getJisuListWithIndustryCode(.....)";

		DAOFactory daoFactory = null;
		try {
			logReq(CUR_METHOD);
			
			String filterFields[] = null;
			
			daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLLOADER_DAO);
			daoFactory.beginTransaction();
			
			JisuDAO jisuDAO = daoFactory.getJisuDAO();
			
			JisuType jisuType = JisuType.valueOf(jisuStr);
			
			List<JisuDTO> list = jisuDAO.getJisuListWithIndustryCode(jisuType, sanId, pumId, fromYYYYMM, toYYYYMM);
			
			if ((list == null) || (list.size() == 0)) {
				logger.error("Failed to find any jisu information");
				return Response.status(Status.NOT_FOUND).entity(generateErrorMessage(ErrorCode.DBIO_DATA_NOTFOUND)).build();
			}
			
			HashMap<String, List<List<Number>>> map = mergeListByJisuName(list);
			
			List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
			
			for (String key : map.keySet()) {
				
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("name", key);
				item.put("sanId", sanId);
				item.put("pumId", pumId);
				item.put("data", map.get(key));
				
				resultList.add(item);
			}
			
			SelectionFilterEnum filter = SelectionFilterEnum.INCLUDES;
			if (filterFields == null) {
				filter = SelectionFilterEnum.ALL;
			}
			
			String entity = serializer.serializeList(resultList, filter, filterFields);
			
			daoFactory.commitTransaction();			
			return Response.ok(entity).build();
			
		} catch (Exception ex) {
			logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);

			ErrorMessage errMsg = generateErrorMessage(ex);
			if (daoFactory != null) daoFactory.rollbackTransaction();
			
			return Response.serverError().entity(errMsg).build();
		} finally {
			if (daoFactory != null) daoFactory.endTransaction();
		}

	}
	
	
	HashMap<String, List<List<Number>>> mergeListByJisuName(List<JisuDTO> jisuList) {
		
		HashMap<String, List<List<Number>>> map = new HashMap<String, List<List<Number>>>();
		
		for (JisuDTO jisuDTO : jisuList) {
			
			List<List<Number>> valueList = map.get(jisuDTO.getJisuName());
			
			// If not exist, create one
			if (valueList == null) {
				valueList = new ArrayList<List<Number>>();
				map.put(jisuDTO.getJisuName(), valueList);
			}
			
			List<Number> pair = new ArrayList<Number>();
			pair.add(jisuDTO.getSecTime());
			pair.add(jisuDTO.getValue());
			
			valueList.add(pair);
		}
		
		return map;
	}

}
