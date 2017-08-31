/**
 * 
 */
package com.mathclub.kit;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.StrKit;

/**
 * @author Administrator
 *
 */
public class StringKit {

	/**
     * 功能：将扩展信息中的形如id=XXXX&from=XXXXX&to=XXXXX参数以键值对的形式获取
     * @param params
     * @return
     */
    public static Map<String, String> putParamsInMap(String params)
    {
        Map<String, String> map = new HashMap<String, String>();
        if (StrKit.isBlank(params))
        {
            return map;
        }
        String s[] = params.split("&");// 以&分割

        for (String string : s)
        {
            String str[] = string.split("=");// 以=分割
            if (str.length < 2)
            {// 有不合法的参数，跳出继续处理后面的参数
                continue;
            }
            if (StrKit.notBlank(str[0]) && StrKit.notBlank(str[1]))
            {
                map.put(str[0], str[1]);
            }
        }
        return map;
    }
}
