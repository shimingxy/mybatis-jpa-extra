/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.apache.mybatis.jpa;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.SimpleStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class ,Integer.class }),
		@Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })  
})
public class AllStatementHandlerInterceptor extends
		AbstractStatementHandlerInterceptor implements Interceptor {
	protected Logger _logger = LoggerFactory.getLogger(AllStatementHandlerInterceptor.class);
	
	public Object intercept(Invocation invocation) throws Throwable {
		Method m = invocation.getMethod();
		if ("prepare".equals(m.getName())) { // prepare Statement
			return prepare(invocation);
		} else if ("parameterize".equals(m.getName())) { // parameterize
			return parameterize(invocation);
		} else if ("handleResultSets".equals(m.getName())) {// handleResultSets
			return handleResultSets(invocation);
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
	

	private Object prepare(Invocation invocation) throws Throwable {
		StatementHandler statement = getStatementHandler(invocation);
		if (statement instanceof SimpleStatementHandler 
				|| statement instanceof PreparedStatementHandler) {
			MetaObject metaObject=SystemMetaObject.forObject(statement);
			Object parameterObject=metaObject.getValue("parameterHandler.parameterObject");
			BoundSql boundSql = statement.getBoundSql();
			String sql = boundSql.getSql();
			_logger.debug("prepare  boundSql : "+sql);
			_logger.debug("startsWith select : "+sql.toLowerCase().trim().startsWith("select"));
			if (sql.toLowerCase().trim().startsWith("select") && (parameterObject instanceof JpaBaseEntity)) {
				if(statement instanceof SimpleStatementHandler){
					sql = dialect.getLimitString(sql, (JpaBaseEntity)parameterObject);
				}else if(statement instanceof PreparedStatementHandler){
					sql = dialect.getPreparedStatementLimitString(sql, (JpaBaseEntity)parameterObject);
				}
			}
			metaObject.setValue("boundSql.sql", sql);
		}
		return invocation.proceed();
	}

	private Object parameterize(Invocation invocation) throws Throwable {
		Statement statement = (Statement) invocation.getArgs()[0];
		if (statement instanceof PreparedStatement) {
			PreparedStatement ps = (PreparedStatement) statement;
			StatementHandler statementHandler = getStatementHandler(invocation);
			RowBounds rowBounds = getRowBounds(statementHandler);
			_logger.debug(rowBounds.toString());
			MetaObject metaObject=SystemMetaObject.forObject(statementHandler);
			Object parameterObject=metaObject.getValue("parameterHandler.parameterObject");
			BoundSql boundSql = statementHandler.getBoundSql();
			
			if (
					boundSql.getSql().toLowerCase().trim().startsWith("select") 
					&& (parameterObject instanceof JpaBaseEntity)
			) {
				List<ParameterMapping>  pms= boundSql.getParameterMappings();
				_logger.debug("ParameterMapping " + pms);
				int parameterSize = pms.size();
				dialect.setLimitParamters(ps, parameterSize,(JpaBaseEntity)parameterObject);
			}
		}
		return invocation.proceed();
	}
	
	private Object handleResultSets(Invocation invocation) throws Throwable {
		//ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
		
		//MetaObject metaObject=MetaObject.forObject(resultSetHandler);
		//RowBounds rowBounds=(RowBounds)metaObject.getValue("rowBounds");
		//if (rowBounds.getLimit() > 0&& rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
		//	metaObject.setValue("rowBounds", RowBounds.DEFAULT);
		//}
		return invocation.proceed();
	}

	
}
