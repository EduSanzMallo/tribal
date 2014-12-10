Project
-------
Build a simple grails application to manage project information.

Description
-----------
The applcation should allow a user to create/edit/delete/view projects.

A project has the following information:
	- project name (alphanumeric)
	- project code (alphanumeric)
	- tech lead (person's name)
	- project manager (person's name)
	- delivery date
	- current phase (one of briefing/scoping/interaction/development/qa/release)
	- priority (numerical)
	
The priority field goes from 1 (highest) upwards and is dependant on the number of projects (e.g. only one project is number 1 priority). 
Kind of like a 'top of the pops' list. 

Any list view should default to ordering by priority.

Extras
------
Think about demonstrating use of tests, maybe some TDD?
TDD consists of creating all tests firstly, run them, wait them to fail and later write the code lines needed to fix those broken tests.

Also the architecture of the application, where should any business logic go?
Any business logic must go inside services as Grails provides you a transactional system to perform all actions related to database 
instead of putting it in controllers as people is accustomed to do it.
