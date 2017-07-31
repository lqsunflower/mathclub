/**
 * 
 */
package com.mathclub.controller;

import com.jfinal.core.Controller;
import com.mathclub.model.User;
import com.mathclub.service.UserService;


/**
 * @author Administratora
 *
 */
public class HelloController extends Controller {
	private UserService service = new UserService();
	
	public void index() {
		
	}
}
