/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 收藏model
 * @author Administrator
 *
 */
public class Favorite extends Model<Favorite>{

	private static final long serialVersionUID = -699456399056744459L;
	
	private int id;//D
	private int userId;//用户id
	private String subjectId;//收藏的题目ID
	
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public String getSubjectId()
    {
        return subjectId;
    }
    public void setSubjectId(String subjectId)
    {
        this.subjectId = subjectId;
    }
	

}
