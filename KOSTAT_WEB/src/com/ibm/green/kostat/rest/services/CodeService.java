package com.ibm.green.kostat.rest.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.kostat.dao.IndustryCodeDAO;
import com.ibm.green.kostat.daofactory.DAOFactory;
import com.ibm.green.kostat.dto.QueryStringDTO;
import com.ibm.green.kostat.rest.AbstractRESTResource;
import com.ibm.green.kostat.rest.ErrorMessage;
import com.ibm.green.kostat.rest.json.SelectionFilterEnum;

@Path("/code")
public class CodeService extends AbstractRESTResource {
	
	@GET
	@Path("{upDown}/{sanId}/{pumId}/{saupId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQueryString(@PathParam("upDown") String upDown, @PathParam("sanId") String sanId, @PathParam("pumId") String pumId,
							@PathParam("saupId") String saupId) {

		final String CUR_METHOD = "CodeService#getQueryString(.....)";

		DAOFactory daoFactory = null;
		try {
			logReq(CUR_METHOD);
			
			String filterFields[] = null;
			
			daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLLOADER_DAO);
			daoFactory.beginTransaction();
			
			IndustryCodeDAO codeDAO = daoFactory.getIndustryCodeDAO();
			
			if (upDown != null) {
				upDown = upDown.toUpperCase();
			}
			
			List<QueryStringDTO> list = codeDAO.getQueryString(sanId, pumId, saupId, upDown);
			
			if ((list == null) || (list.size() == 0)) {
				logger.error("Failed to find any jisu information");
				return Response.status(Status.NOT_FOUND).entity(generateErrorMessage(ErrorCode.DBIO_DATA_NOTFOUND)).build();
			}

			SelectionFilterEnum filter = SelectionFilterEnum.INCLUDES;
			if (filterFields == null) {
				filter = SelectionFilterEnum.ALL;
			}
			
			String entity = serializer.serializeList(list, filter, filterFields);
			
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
	
}
