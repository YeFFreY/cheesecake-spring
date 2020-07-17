package org.yeffrey.cheesecakespring.infrastructure;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/*
@Profile("integration")
@Configuration
@EnableWebSecurity
*/
public class TestSecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {
    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().permitAll();

    }
/*
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AccessToken accessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.setSubject("abc");
        accessToken.setName("Tester");

        return accessToken;

    }*/

}