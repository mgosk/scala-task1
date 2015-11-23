## what scala mechanism have you used and why ? what are their advantages, why you choose this way or not another ? do you see any disadvantages of your solution ?

* Simple dependency injection  - it allow us to prepare mock for unit tests
* pattern matching - clean code
* sealed traits/classes - avoid runtime error on pattern matching
* stream - clean way for reading commands 


## Mutable vs Immutable - which collections have you used and why?

I used mutable collections, because it is a convenient way to store changeable state. Using immutable collection is very 
important in multithreaded apps. It allow thread-safe access without locks and synchronization.

## Is it easy to test your application. Why? :)

Yes. App is divided into few classes and all dependencies are injected via constructor. One possible facilitation is separating
interfaces from logic. 

## What will happen if we add another role to the system ? RichCustomer which can be a Customer but not an Admin ? MarketingManager who can be Admin but not a Customer ? 

Current priv and roles mechnism is fully extensible. Programmer can add more role without any problems. 

## Is it easy to additional items to the store? What if certain items will have unique properties?

Yes, adding another items to store is possible, but require app recompilation. I defined items as scala objects, it allow us 
to extend selected items with unique properties with checking in compilaton time.

## What can you say about package structure?

The program is very small and I resigned form package structure. Instead that I logically distributed classes into few files.

## Is it easy to maintain your application? What level of knowledge is needed to work with your code?

Partially yes. Class names are intuitive, code well formatted but I don't document this code. All public function should be
documented and general app architecture described in separate file.
 
