package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;

@RestController
@RequestMapping("/api/login")
@Api(value = "登陆", tags = {"登陆接口"})
public class LoginApi {

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登陆", tags = {"登陆接口"}, notes = "登陆")
    public ActionResult<String> getAllPersonNamesWithTraditionalWay(@RequestBody LoginDto dto) {

        String msg = getAllPersonNamesWithTraditionalWay(dto.getUsername(), dto.getPassword());
        return new ActionResult<>(msg);
    }

    @RequestMapping(value = "/getMsg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登陆", tags = {"登陆接口"}, notes = "登陆")
    public String getAllPersonNamesWithTraditionalWay(@RequestParam String username, @RequestParam String password) {
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://10.75.10.250:389/dc=ansell,dc=com");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx;
        String name = "";
        NamingEnumeration results = null;
        try {
            ctx = new InitialDirContext(env);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search("", "(&(objectclass=person)(userprincipalname=" + username + "))", controls);
            SearchResult searchResult = (SearchResult) results.next();
            Attributes attributes = searchResult.getAttributes();
            name = attributes.get("userprincipalname").get().toString().split("@")[0];
        } catch (AuthenticationException e) {
            String erroMsg = e.toString();
            e.printStackTrace();
            return erroMsg;
        } catch (NameNotFoundException e) {
            String erroMsg = e.toString();
            e.printStackTrace();
            return erroMsg;
        } catch (NamingException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return name;
    }

}