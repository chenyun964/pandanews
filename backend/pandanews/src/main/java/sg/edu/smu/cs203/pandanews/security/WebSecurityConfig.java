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

                // authentication API
                .antMatchers(HttpMethod.POST, "/authentication", "/registration", "/admin/authentication", "/admin/registration").permitAll()
                
                // swagger API
                .antMatchers("/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui.html#/").permitAll()                // API Under development

                // role-specific requests
                // users API
                .antMatchers("/users/**").authenticated()

                // image API
                .antMatchers("/image").permitAll()
                .antMatchers(HttpMethod.POST, "/image/**").hasRole("ADMIN")

                // news API
                .antMatchers(HttpMethod.GET, "/news/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/news/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/news/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/news/**").hasRole("ADMIN")

                 // measurements API
                .antMatchers(HttpMethod.GET, "/measurements/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/measurements/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/measurements/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/measurements/**").hasRole("ADMIN")

                // organisations API
                .antMatchers(HttpMethod.GET, "/organisations/*/workgroups", "/organisations/*/workgroups/*").hasAnyRole("OWNER", "MANAGER")
                .antMatchers(HttpMethod.POST, "/organisations/*/workgroups").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/organisations/*/workgroups/*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.DELETE, "/organisations/*/workgroups/*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/organisation/promotion/**").hasRole("OWNER")
                .antMatchers(HttpMethod.PUT, "/organisation/demotion/**").hasRole("OWNER")
                .antMatchers("/organisation/employee/**").hasAnyRole("OWNER", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/organisation/approval/*").hasRole("ADMIN")
                
                // policies API
                .antMatchers(HttpMethod.GET, "/organisation/*/policies/*").authenticated()
                .antMatchers(HttpMethod.POST, "/organisation/*/policies").hasAnyRole("OWNER", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/organisation/*/policies/*").hasAnyRole("OWNER", "MANAGER")
                .antMatchers(HttpMethod.DELETE, "/organisation/*/policies/*").hasAnyRole("OWNER", "MANAGER")

                // category API
                .antMatchers(HttpMethod.GET, "/category/**").permitAll()
                .antMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")

                // vaccispots API
                .antMatchers(HttpMethod.GET, "/vaccispots/**").permitAll()
                .antMatchers(HttpMethod.POST, "/vaccispots/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/vaccispots/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vaccispots/*").hasRole("ADMIN")

                // testspots API
                .antMatchers(HttpMethod.GET, "/testspots/**").permitAll()
                .antMatchers(HttpMethod.POST, "/testspots/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/testspots/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/testspots/*").hasRole("ADMIN")

                // statistic API
                .antMatchers(HttpMethod.GET, "/statistic/**").permitAll()
                .antMatchers(HttpMethod.POST, "/statistic/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/statistic/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/statistic/**").hasRole("ADMIN")

                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to store user's state
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}