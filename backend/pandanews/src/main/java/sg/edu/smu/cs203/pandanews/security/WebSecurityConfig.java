package sg.edu.smu.cs203.pandanews.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.cors().and().csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                // Production API
                .antMatchers("/authenticate", "/register", "/admin/authenticate", "/admin/register").permitAll()
                .antMatchers("/organisation/approve/*").hasRole("ADMIN")
                .antMatchers("/organisation/employee").hasAnyRole("ADMIN", "MANAGER", "OWNER")
                .antMatchers("/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui.html#/",
                        // Auth API
                        "/authenticate",
                        "/register",
                        "/confirmemail",
                        "/reset",
                        "/resetpassword",
                        // User API
                        "/user/forget/**").permitAll()                // API Under development
                // role-specific requests
                .antMatchers(HttpMethod.GET, "/organisations/*/workgroups", "/organisations/*/workgroups/*").permitAll()
                .antMatchers(HttpMethod.GET, "/news/**").permitAll()
                .antMatchers(HttpMethod.POST, "/news/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/news/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/news/**").permitAll()
                .antMatchers(HttpMethod.GET, "/category/*").permitAll()
                .antMatchers(HttpMethod.POST, "/category/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/category/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/category/*").permitAll()
                .antMatchers("/measurements/*").permitAll()
                .antMatchers("/measurements").permitAll()
                .antMatchers("/covid/*").permitAll()
                .antMatchers("/covid").permitAll()
                .antMatchers("/image").permitAll()
                .antMatchers(HttpMethod.POST, "/organisations/*/workgroups").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/organisations/*/workgroups/*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.DELETE, "/organisations/*/workgroups/*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/organisation/promote/*").hasRole("OWNER")
                .antMatchers("/organisation/demote/*").hasRole("OWNER")
                .antMatchers("/organisation/employee/*").hasAnyRole("OWNER", "MANAGER")
                .antMatchers("/organisation/**").authenticated()
                .antMatchers("/users/**").authenticated()

                .antMatchers(HttpMethod.GET, "/vaccispots/**").permitAll()
                .antMatchers(HttpMethod.POST, "/vaccispots/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/vaccispots/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/vaccispots/**").permitAll()

                .antMatchers(HttpMethod.GET, "/testspots/**").permitAll()
                .antMatchers(HttpMethod.POST, "/testspots/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/testspots/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/testspots/**").permitAll()

                .antMatchers(HttpMethod.GET, "/statistic/**").permitAll()
                .antMatchers(HttpMethod.POST, "/statistic/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/statistic/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/statistic/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/statistic/**").permitAll()

                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to store user's state
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}