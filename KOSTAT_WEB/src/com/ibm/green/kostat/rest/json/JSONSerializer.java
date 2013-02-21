package com.ibm.green.kostat.rest.json;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.exception.SerializationException;

/**
 * Simple JSON serializer wrapper class
 * 
 * <p>It provides  conversion from <code>Object</code> to JSON <code>String</code>.
 * The concrete serializer class must implement <code>serializeImpl(Object):String</code> method.
 * 
 * <p>This class provides dynamic field filtering for serialization via method 
 * <code>serialize(Object object, SelectionFilterEnum filter, String[] propertyNames)</code>.
 * If you call this function with <code>SelectionFilterEnum.INCLUDES</code>, only specified properties 
 * will be serialized. If you call the function with <code>SelectionFilterEnum.EXCLUDES</code>, specified
 * properties will be excluded from serialization. If called with <code>SelectionFilterEnum.ALL</code>,
 * all properties will be serialized regardless of <code>propertyNames</code>.
 */
public abstract class JSONSerializer {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	public final static int FAKE = 0;
	public final static int JACKSON = 1;
	
	/**
	 * Simple factory method
	 * 
	 * @param type FAKE, JACKSON
	 * @return
	 */
	public static JSONSerializer getSerializer(int type) {
		
		switch (type) {
		case FAKE:
			return new FakeJSONSerializer();
		case JACKSON:
			return new JacksonJSONSerializer();
		default:
			throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, "Unsupported JSON Serializer was chosen. type :" + type);
		}
	}
	
	/**
	 * Field filtering serialization method.
	 * 
	 * <p>You can adjust the field list to be serialized dynamically with two parameters
	 * <code>filter</code> and <code>propertyNames</code>. All fields are examined by JavaBeans
	 * property name rule. Each selected field will be serialized by concrete JSON serializer that extends
	 * this class.
	 * 
	 * <p>If object is an instance of <code>Collection</code>, it will be serialized by concrete JSON
	 * serializer directly. In this case, only <code>SelectionFilterEnum.ALL</code> is supported. 
	 * If other filter type is specified, <code>IllegalArgumentException</code> will be thrown.
	 * 
	 * <p>If filter type is not ALL, <code>propertyNames</code> must not be null and empty.
	 * If not,  <code>IllegalArgumentException</code> will be thrown.
	 * 
	 * <p>If <code>nameReplaceMap</code> is given and the property name match any key in the Map,
	 * value will be used as a name instead of original name
	 * 
	 * @param object to be serialized.
	 * @param filter type of filed filtering. ALL/INCLUDES/EXCLUDES
	 * @param propertyNames property names to be filtered in or out. In case of filter is ALL, this is ignored.
	 * @param nameReplaceMap map contains the original property name as a key and replace name as a value
	 * @return serialized JSON String
	 * @throws SerializationException 
	 * @throws IllegalArgumentException if propertyNames is null or empty when filter is INCLUDES or EXCLUDES
	 */
	public String serialize(Object object, SelectionFilterEnum filter, String[] propertyNames, Map<String, String> nameReplaceMap) 
			throws SerializationException, IllegalArgumentException {
		
		StringBuilder stb = new StringBuilder("{");
		try {
			
			if ((filter != SelectionFilterEnum.ALL) && ((propertyNames == null) || (propertyNames.length == 0))) {
				throw new IllegalArgumentException("properyNames must not be null or empty if filter is not ALL.");
			}
			
			// In case of Collection
			if ((object instanceof Collection<?>) || (object instanceof Map<?, ?>)) {
				if (filter != SelectionFilterEnum.ALL) {
					throw new IllegalArgumentException("filter must be ALL if object is instance of Collection.");
				}
				
				return serializeImpl(object); // call concrete impl directly.
			}
			
			// create inclusion evaluator 
			SelectionEvaluator evaluator = createEvaluator(filter, propertyNames);
			
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
			PropertyDescriptor pds[] = beanInfo.getPropertyDescriptors();
			
			if (pds == null) {
				logger.error(object.getClass().toString() + "'s BeanInfo.getPropertyDescriptors returns null");
				throw new SerializationException();
			}
			
			if (pds.length == 0) { // It's not a bean
				return serializeImpl(object); // call concrete impl directly.
			}

			boolean bFirst = true;
			for (PropertyDescriptor pd : pds) {
				
				String propName = pd.getName();
				
				// check this property is included or not
				if (!evaluator.isIncluded(propName)) continue;

				// retrieve the property value
				Method readMethod = pd.getReadMethod();
				Object propValue = readMethod.invoke(object);
				
				// serialize property's value object with concrete serializer
				String strValue = serializeImpl(propValue);

				if (!bFirst)  {
					stb.append(",");				
				}
				bFirst = false;
				
				// if replacement name is provided, use it as a key
				if  ((nameReplaceMap != null) && (nameReplaceMap.get(propName) != null)) {
					propName = nameReplaceMap.get(propName);
				}
				
				stb.append(" \"").append(propName).append("\" : ").append(strValue);
			}
			
		} catch (IntrospectionException ex) {
			logger.error("Failed to introspect clazz " + object.getClass() + " to serialize.", ex);
			throw new SerializationException(ex);
		} catch (InvocationTargetException ex) {
			logger.error("Failed to introspect clazz " + object.getClass() + " to serialize.", ex);
			throw new SerializationException(ex);
		} catch (IllegalAccessException ex) {
			logger.error("Failed to introspect clazz " + object.getClass() + " to serialize.", ex);
			throw new SerializationException(ex);
		}

		return stb.append("}").toString();
	}
	
	/**
	 * Field filtering serialization method.
	 * 
	 * <p>You can adjust the field list to be serialized dynamically with two parameters
	 * <code>filter</code> and <code>propertyNames</code>. All fields are examined by JavaBeans
	 * property name rule. Each selected field will be serialized by concrete JSON serializer that extends
	 * this class.
	 * 
	 * <p>If object is an instance of <code>Collection</code>, it will be serialized by concrete JSON
	 * serializer directly. In this case, only <code>SelectionFilterEnum.ALL</code> is supported. 
	 * If other filter type is specified, <code>IllegalArgumentException</code> will be thrown.
	 * 
	 * <p>If filter type is not ALL, <code>propertyNames</code> must not be null and empty.
	 * If not,  <code>IllegalArgumentException</code> will be thrown.
	 * 
	 * @param object to be serialized.
	 * @param filter type of filed filtering. ALL/INCLUDES/EXCLUDES
	 * @param propertyNames property names to be filtered in or out. In case of filter is ALL, this is ignored.
	 * @return serialized JSON String
	 * @throws SerializationException 
	 * @throws IllegalArgumentException if propertyNames is null or empty when filter is INCLUDES or EXCLUDES
	 */
	public String serialize(Object object, SelectionFilterEnum filter, String[] propertyNames) 
			throws SerializationException, IllegalArgumentException {
		return this.serialize(object, filter, propertyNames, null);
	}
			
	
	/**
	 * Serialize Java object.
	 * It will be same with <code>serialize(object, SelectionFilterEnum.ALL, null)</code>
	 * 
	 * @param object
	 * @return
	 * @throws SerializationException
	 */
	public String serialize(Object object) throws SerializationException {
		return serialize(object, SelectionFilterEnum.ALL, null, null);
	}
	
	/**
	 * Serialize the given <code>List</code>.
	 * <p>
	 * Each element in the list will be serialized with field selection by <code>filter</code>
	 * and <code>propertyNames</code>
	 * 
	 * <p>If <code>nameReplaceMap</code> is given and the property name match any key in the Map,
	 * value will be used as a name instead of original name
	 * 
	 * @param list
	 * @param filter type of filed filtering. ALL/INCLUDES/EXCLUDES
	 * @param propertyNames property names to be filtered in or out. In case of filter is ALL, this is ignored.
	 * @param nameReplaceMap map contains the original property name as a key and replace name as a value
	 * @return serialized JSON String
	 * @throws SerializationException 
	 */
	public <E> String serializeList(List<E> list, SelectionFilterEnum filter, String[] propertyNames, Map<String, String> nameReplaceMap) 
			throws SerializationException {
	
		StringBuilder stb = new StringBuilder("[");
		boolean bFirst = true;
		for (E ele : list) {

			if (!bFirst) {
				stb.append(", ");
			}
			bFirst = false;
			
			String elementStr = this.serialize(ele, filter, propertyNames, nameReplaceMap);
			stb.append(elementStr);
		}
		
		return stb.append("]").toString();
	}
	
	
	
	/**
	 * Serialize the given <code>List</code>.
	 * <p>
	 * Each element in the list will be serialized with field selection by <code>filter</code>
	 * and <code>propertyNames</code>
	 * 
	 * @param list
	 * @param filter type of filed filtering. ALL/INCLUDES/EXCLUDES
	 * @param propertyNames property names to be filtered in or out. In case of filter is ALL, this is ignored.
	 * @return serialized JSON String
	 * @throws SerializationException 
	 */
	public <E> String serializeList(List<E> list, SelectionFilterEnum filter, String[] propertyNames) 
			throws SerializationException {

		return serializeList(list, filter, propertyNames, null);
	}
	
	/**
	 * Serialize the given <code>Map</code>
	 * <p>
	 * If the map has already serialized String of JSON array or object and you use the normal serialization,
	 * the contents will be enclosed by double quote(") because the serializer will treats them as string. 
	 * If you don't want to happen this situation, just specify keys whose value you want to preserve in
	 * <code>preserveFields</code>. Those values will be not be passed to JSON serializer and just appended 
	 * into the result after callin toString().
	 * 
	 * @param map
	 * @param preserveFields 
	 * @return
	 * @throws SerializationException
	 */
	public String serializeMap(Map<String, ? extends Object> map, String[] preserveFields) throws SerializationException {
		StringBuilder stb = new StringBuilder("{");

		HashSet<String> preserveFieldsSet = null;
		if (preserveFields != null) {
			preserveFieldsSet = new HashSet<String>(Arrays.asList(preserveFields));			
		}

		boolean bFirst = true;
		for (String key : map.keySet()) {
			
			if (!bFirst) {
				stb.append(", ");
			}
			bFirst = false;
			
			String strValue;

			if ((preserveFieldsSet != null) && (preserveFieldsSet.contains(key))) {
				strValue = map.get(key).toString(); // preserve the contents
			} else {
				strValue = this.serializeImpl(map.get(key)); // pass to JSON serializer
			}
			stb.append(" \"").append(key).append("\" : ").append(strValue);
		}

		stb.append(" }");

		return stb.toString();
	}
	
	
	/*
	 * Concrete serialization implementation
	 */
	abstract String serializeImpl(Object object) throws SerializationException;
	
	
	interface SelectionEvaluator {
		boolean isIncluded(String propName);
	}
	
	/*
	 * Simple field evaluator function creator
	 */
	SelectionEvaluator createEvaluator(SelectionFilterEnum filter, String[] propNames) {
		
		final HashSet<String> propSet;
		
		switch (filter) {
		case ALL:
			return new SelectionEvaluator() {
				@Override
				public boolean isIncluded(String propName) {
					return true;
				}
			};

		
		case INCLUDES:
			propSet = new HashSet<String>(Arrays.asList(propNames));
			return new SelectionEvaluator() {
				@Override
				public boolean isIncluded(String propName) {
					return propSet.contains(propName);
				}
			};
			
		case EXCLUDES:
			propSet = new HashSet<String>(Arrays.asList(propNames));
			return new SelectionEvaluator() {
				@Override
				public boolean isIncluded(String propName) {
					return (!propSet.contains(propName));
				}
			};
			
		default:
			throw new IllegalArgumentException("Unknown SelectionFilterEnum value : " + filter);
		}
	}

}
