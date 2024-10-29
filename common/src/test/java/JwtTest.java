import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.forfries.constant.JwtClaimsConstant;
import com.forfries.utils.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtTest {
    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, "123456");
        claims.put(JwtClaimsConstant.ROLE,
                    JwtClaimsConstant.ROLE_USER);
        claims.put(JwtClaimsConstant.CINEMA_ID, "123");
        String jwt = JwtUtil.createJWT("com.forfries", 100000000, claims);
        Map<String, Claim> stringClaimMap = JwtUtil.decodeJWT("com.forfries", jwt);
        System.out.println(stringClaimMap);

    }
}
