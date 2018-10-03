


                       _  _  _  _  ____  _  _  __  __    ____   ___  _  _  ____  _  _ 
                      ( \/ )( \/ )(  _ \/ )( \(  )(  )  (    \ / __)/ )( \(  _ \/ )( \
                      / \/ \ )  /  ) _ () \/ ( )( / (_/\ ) D (( (_ \) \/ ( )   /) \/ (
                      \_)(_/(__/  (____/\____/(__)\____/(____/ \___/\____/(__\_)\____/



This is a collection of projects which I built with a bunch of fresh out of college kids to help my friend's startup in Bengaluru. Started in 2015, I wanted to build a set of microservices to support an ecommerce like website for building materials and home services. Unfortunately the friends did not stay as a pack and this project folded up. I will write another blog post explaining about it. 

Initially I had planned a bunch of microservices using Spring Boot, it was the new thing in 2015 :). But by the time I could make the team understand it, it ended up being 3 services, API, Email and SMS. The project tried to follow the Hybrid Data model with authentication and transactions on MariaDB and Products/Services on MongoDB all hosted on a set of virtual machines. This project had Free Azure usage support from Microsoft's Bizpark initiative. So I got my hands dirty with Azure services like 
-Virtual machines
-VPC
-Service Bus and 
-CDN 

We had integrations with SMS service (SMSGupshup) to do campaigns and send transaction sms. Integration with CCAvenue for payment gateway etc. were added. The UI piece was built using Angular 1.x and I have a different story to tell regarding the experience of building/getting it built!!

All things have an end and so was my little adventure. It took me a while to get this code migrated from the SVN repo to GIT. This code base will stay as a reminder of my failure and its learning!! 

-Naveen 

