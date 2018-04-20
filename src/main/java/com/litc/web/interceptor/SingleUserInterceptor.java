package com.litc.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.UserCacheManager;

public class SingleUserInterceptor implements HandlerInterceptor {
	
	private final static Logger logger = LoggerFactory.getLogger(SingleUserInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String sessionId = (String)session.getAttribute("sessionId");
		String loginName = (String)session.getAttribute("loginName");
		String thisSessionId = UserCacheManager.getUserSessionId(loginName);
		if(StringUtils.isNotBlank(sessionId)&&StringUtils.isNotBlank(thisSessionId)){
			if(!sessionId.equals(thisSessionId)){
				session.invalidate();//清空session
				response.sendRedirect(request.getContextPath()+"/logout");
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
