# CodeExamples
This repo is for demonstration purposes to show some basic code mistakes/inadequacies and a version of more or less the same code that does not make these mistakes. 
The 'bad' Code is in branch badCode and the 'good' code is in branch goodCode

The issues with the 'bad' Code are the following :
1)all of the business logic of the application is placed in the controller.
2)one model is being used for input and output of information
3)the dependencies are autowired directly on the property level making it very hard to unit test application
4)For transformation a simple constructor is used which is not very flexible or maintainable
5)Filtering strategy is a mess
6)endpoints which do not need to exist are present  

The 'good' code has the following benefits/ good practises followed:
1) A separate service layer exists where the business logic of the application can be created and maintained
2) Separate models exist for payload of requests/ output of api's
3) The builder pattern is used instead of constructors for initialising of objects which is more flexible/future-proof
4)The dependencies are autowired on constructor level making it easier to unit test application
5) filtering of information is done directly on the get Api level
6)no unnecessary endpoints
7) the controllers are only used for receiving and returning information
8) the repositories are only used for getting information from database
9) no floating strings are present in the code. Application uses a central utility file for constants.

Ways in which the 'good' code can ge enhanced/made better
1) Custom exception can be used to return more user friendly information/ error message
2) Filtering strategy can be enhanced/reworked to be much more flexible
3) some level of abstraction can be implemented to remove some boilerplate code
4) custom implementation of builder pattern can be used instead of default lombok implementation in the case of some future requirement that would mandate customizing the builder behaviour


An more advanced variant of a similar application containing examples of some of these good characteristinc can be viewed here : https://github.com/MNP160/farmersApi (application is in c# though)
That code however has other issues/ 'bad' code which is mostly incorrect implementation of some functionalities.
