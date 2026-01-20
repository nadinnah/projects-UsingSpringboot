package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
//customizing jpa repo notes:

    //List<Order> findByDeliveryZip(String deliveryZip);
    //Repository methods are composed of a verb, an optional subject, the word By, and a predicate. In the case of findByDelivery
    //Zip(), the verb is find and the predicate is DeliveryZip; the subject isn’t specified and is implied to be an Order.

    //Spring Data ignores most words in a subject, so you could name the method readPuppiesBy...
    //and it would still find Order entities,

    //(read/find/get are for fetching)By(match .deliveryZip/.delivery.zip property) and (match .placedAt/.placed.at property) (between=value must fall between given values)
   // List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    //can use count instead of read/get/find if i only want the method to return an int with the count of matching entities.
}
//When generating the repository implementation, Spring Data examines any methods in the repository interface, parses the method name, and attempts to understand the
//method’s purpose in the context of the persisted object (an Order, in this case).

//Spring Data defines a sort of miniature domain-specific language (DSL) where persistence details are expressed in repository method signatures.

//Spring Data method signatures can also include any of these operators:
// IsAfter, After, IsGreaterThan, GreaterThan
// IsGreaterThanEqual, GreaterThanEqual
// IsBefore, Before, IsLessThan, LessThan
// IsLessThanEqual, LessThanEqual
// IsBetween, Between
// IsNull, Null
// IsNotNull, NotNull
// IsIn, In
// IsNotIn, NotIn
// IsStartingWith, StartingWith, StartsWith
// IsEndingWith, EndingWith, EndsWith
// IsContaining, Containing, Contains
// IsLike, Like
// IsNotLike, NotLike
// IsTrue, True
// IsFalse, False
// Is, Equals
// IsNot, Not
// IgnoringCase, IgnoresCase


//method names could get out of hand for more complex queries. In that case, name the
//method anything you want and annotate it with @Query to explicitly specify the query to be performed
//@Query("Order o where o.deliveryCity='Seattle'")
//List<Order> readOrdersDeliveredInSeattle();