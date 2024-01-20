## File management API (demo) 
A spring boot application for managing upload/download files

## Note : 
This is a very simple application to prove the ability to develop and organize applications.
It's need some improvement to be better.

## Tools and technologies 
	1. java 11
	2. spring boot 2.7.8
	3. postgres database 
    4. dokcer

## How to start application ?
For simplicity, I used the local registry to build my docker image instead of pushing it to remote one.  

- Pre prerequisite
    1. docker installed on your machine  
    
  - Steps

      * windows : 
      
        a. Double-click on start.bat

        b. After all thing is done just open your browser and
              navigate to http://localhost:9999/swagger-ui/index.html#/       
              that will take you to openApi documentations (swagger).
      * linux : 
    
        a. Run file start.sh
    
        b. After all thing is done just open your browser and 
               navigate to http://localhost:9999/swagger-ui/index.html#/ 
               that will take you to openApi documentations (swagger).
            
## Note
For simplicity, I didn't manage a full authentication life cycle , just validating the permission using custom Method Security Expression which use email sent on the request. 

## References 
1. https://medium.com/@islamboulila/how-to-create-a-custom-security-expression-method-in-spring-security-e5b6353f062f
2. https://satyacodes.medium.com/a-complete-guide-to-spring-security-with-springboot-329a959a7c64
3. https://dev.to/tienbku/docker-compose-spring-boot-and-postgres-example-4l82


