package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.util.JwtUtils;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public class Test {
    @org.junit.Test
    public void test() {
        String token = JwtUtils.getJwtToken("12", "cwl");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("ukc8BDbRigUDaY6pZFfWus2j").parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println((String)claims.get("id"));
    }
}
