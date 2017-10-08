package mathclub;

import org.junit.Test;

import com.jfinal.kit.HashKit;

public class S {

	@Test
	public void a() {
		// 密码加盐 hash
		String salt = HashKit.generateSaltForSha256();
		System.out.println(salt);
		String password = HashKit.sha256(salt + "111111");
		System.out.println(password);//76823e506aef7f8dee7c479bc5991430c25e2a2167efbbb69de2a1a248483b82
		System.out.println(HashKit.sha256("N6JjRhujp6U8l4Yu7vuQDZtest"));
	}
}
