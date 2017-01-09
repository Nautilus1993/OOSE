#API specification
##Part 1: Register/Login 

### 1. Registration -- email verification
+ **POST:** /registration/email
    
+ verify an email address when a user creates an account.
    
+ Body: 

	 	{
			"sessionId" : "<String>",
			"email" : "<String>"
		}
    

+ Success: 201 (Success, email is verfied!)

+ Response:

		 {
		 		"emailSend" : "<String>"
		 }
    
+ Failure: 
     + 404 (Email address already exists)

---

### 2. Registeration -- phone verification( * 2 steps)

+ **POST:** /registration/phoneSendCode
    
+ send verification message to the phone number when a user creates an account.
    
+ Body:
 
        {
			"sessionId" : "<String>",
			"PhoneNumber" : "<String>"
		}
        
+ Success: 201 (Success, verification code is sent!)

+ Response:

		{
			"emailSend" : "<String>"
		}
        

+ Failure: 
    + 404 (Phone number already exists)

---


+ **POST:** /registration/phoneVerifyCode
    
+ Check the verification code typed by user.
    
+ Body:

		{
			"sessionId" : "<String>",
			"PhoneNumber" : "<String>",
			"SecurityCode" : "<String>"
		}
	  
+ Success: 201 (Success, phone is verfied!)

+ Response:
			
			{
				"verified" : "<boolean>"
			}


+ Failure: 
       
      + 404 (fail to verify)

---

###3. Registeration -- Password confirm
+ **POST:** /registration/comfirmPwd
   
+ confirm the passwords user enters

+ Body:
 
        
        {
        	"sessionId" : "<String>"
        	"password" : "<String>"
        }
        
   
+ Success: 201 (Success, OK)
 
+ Failure: 
        
      + 450 (no corresponding username)
        
---

###4. Login

+ **PUT:** /login
   
+ Sign in an account.

+ Body:

	       
	      {
	      		"email" : "<String>"
	      		"password" : "<String>"
	      }
   
+ Success: 200 (Success, OK)
   
+ Response: 
       
	       {
	       		"sessionId" : "<String>"
	       }

       
+ Failure:
       + 404 (Invalid username)  
       + 410 (username and password don't match)

---

###5. logout

+ **PUT:** /logout/:userId
+ Sign out an account.
+  Params:

      	userId: <int> the Id of the user.
     
+ Success: 200 (Success, OK) 
+ Response: 

       	{ "isLogout" : "<boolean>" } 
       
+ Failure:
      + 451 (Fail to logout)
       
##Part 2: Home Page

### 1. Home Page 

+ **GET:**  /item/topCategory
+ Display the top level categories.
+ Success:  200 (Success, OK)
+ Response:

		{
	       topLevelCategories: [
	           "name": "<String>"
	           "imgPath": "<String>" 
	       ]
      	}

---
	       
###2. Sub-level category

+ **GET:**  /item/subCategory/:topCatId
+ Display the sub-level categories of a top-level category.
   
+ Params:

        category1: <String>, the name of the top-level category
        
+ Success: 200 (Success, OK)
+ Response:

	       subLevelCategories: [
	           name: <String>, The sub-level category name.
	           imgPath: <String> The path on the disk where the images of sub-level categories are stored
	       ]
   
+ Failure:
       + 404: (Invalid top-level category)

---

###3. Item List
+ **GET:** /item/itemList/:subCatId
+ Display the items in that sub-level category.
   
+ Params:

        category2: <String>, the name of the sub-level category
   
+ Body:

        sessionId: <String>, the sessionId of the current user

+ Success: 200 (Success, OK)
   
+ Response:

		{
			itemShortcut: [
	           "name": "<String>",
	           "price": "<float>", 
	           "itemImgPath": "<String>", 
	           "isDeliver": "<boolean>", 	           
	           "sellerImgPath": "<String>", 
	           "distance": "<float>", 
	           "isLiked": "<boolean>"
	       ]
		}
	
	       
   
+ Failure:
       
    + 404: (Invalid sub-level category)

---

###4. Item Detail 

+ **GET:** /item/itemDetail/:itemId
+ Display the detail of an item.

+ Params:
        itemId: <int>, the id of the item
   
+ Body:

        sessionId: <String>, the sessionId of the current user

+ Success: 200 (Success, OK)
+ Response:
	 
	       {
				"name" : "<String>" ,
				"description" : "<String>" ,
				"price" : "<float>" ,
				"itemImgPath" : "<String>" ,
				"isDeliver" : "<boolean>" ,
				"sellerName" : "<String>" ,
				"expireDate" : "<Date>" ,
				"availableDate" : "<Date>" ,
				"sellerImgPath" : "<String>" ,
				"distance" : "<float>" ,
				"isLiked" : "<boolean>" ,
				"email" : "<String>" ,
				"phone" : "<String>" ,
				"facebookLink" : "<String>" 
			}
+ Failure:
       + 404: (Invalid itemId)

##Part 3: MyAccount

###1. Create a new Account (edit profile for the first time)

+ **POST:** /MyAccount/AcountCreate/ 

+ create an user's account (for the first time login)

+ Body:

		{	"sessionID": <String>,   // IMEI
			"username" : <String>,   
			"email" : <String>,
			"phone" : <String>,
			"address" : <String>,
			"city" : <String>,
			"state" : <String>,
			"zipcode" : <String>,
			"facebook" : <String>,
			"imgLink" : <String>
		}

+ Success:  201 （Success, created）

+ Failure:
	+ 404 (invalid userName)
	+ 400 (Create fail: Info is not complete)
	
---

###2. Update user profile
+ **POST:** /MyAccount/AcountModify/:sessionId

+ modify user's account

+ Params:

     	sessionId <String>

+ Body:

		{   	
			"email" : <String>,
			"phone" : <String>,
			"password" : <String>,
			"addrss" : <String>,
			"city" : <String>,
			"state" : <String>,
			"zipcode" : <String>,
			"facebook" : <String>,
			"imgLink" : <String>
		}

+ Success:  201 （Success, update）

+ Failure:
	+ 404 (invalid session id)
	+ 400 (update fail: Info is not complete)

##Part 4: Selling List

###1. Display an user's selling list 
+ **GET:** /sellList/display/:sessionId

+ Get the selling list of the user.

+ Params:

		sessionId: <String> The ID of the user to get information from.

+ Success: 200 (Success, OK)

+ Response:
 
			{
				"sellingList": [{
					"name": "Balck Bed",
					"type": "Delivery",
					"itemId": "<Item Id>",
					"price": "<Price>",
					"distance": "<Distance>",
					"sellerId": "<seller Id>"
				}, {
					"name": "Balck Bed",
					"type": "Delivery",
					"itemId": "<Item Id>",
					"price": "<Price>",
					"distance": "<Distance>",
					"sellerId": "<seller Id>"
				} ...]
			}

---

###2. Post an item
+ **POST:** /sellList/post/:sessionId

+ Post an item. (use userName as sessionId)

+ Params:

     	sessionId: <String>, the ID of the User.

+ Body:



	     {
				"topLevel" : "<String>",
				"secondLevel" : "<String>",
				"name" : "<String>",
				"price" : "<double>",
				"condition" : "<String>",
				"ifDeliver" : "<boolean>",
				"pickUpAddress" : "<String>",
				"desciption" : "<String>",
				"postDate" : "<Date>",
				"avialableFrom" : "<Date>",
				"avialableUntil" : "<Date>",
				"contactMethods":[{
						"Facebook" : "fbAccount",
						"Gmail" : "gmailAddress",
						...
					},{
						"Facebook" : "fbAccount",
						"Gmail" : "gmailAddress",
						...
					} ...]
			}

+ Success: 201 (Success, created)

+ Response:

     	itemId: <String>, the ID for the item.

+ Failure:
     + 460 (Item can not be found!)
     + 461 (user not found!)

---

### 3. Edit an posted item
+ **POST:** /sellList/edit/:itemId

+ Edit a posted item.

+ Params:

	 	itemId: <int>, the ID of the Item.

+ Body:

		{
			"topLevel" : "<String>",
			"secondLevel" : "<String>",
			"name" : "<String>",
			"price" : "<double>",
			"condition" : "<String>",
			"ifDeliver" : "<boolean>",
			"pickUpAddress" : "<String>",
			"desciption" : "<String>",
			"postDate" : "<Date>",
			"avialableFrom" : "<Date>",
			"avialableUntil" : "<Date>",
			"contactMethods":[{
					"Facebook" : "fbAccount",
					"Gmail" : "gmailAddress",
					...
				},{
					"Facebook" : "fbAccount",
					"Gmail" : "gmailAddress",
					...
				} ...]
		}

+ Success: 201 (Success, edited)

+ Failure:
     + 462 (Invaild itemId)
     + 463 (Item can not be edited!)
     




##Part 5: Wish List

###1. Display an user's wishlist

+ **GET:** /WishList/get/:sessionId

+ Get the wish list of the user.

+ Params:

		sessionId: <String> The ID of the user to get information from.

+ Success: 200 (Success, OK)

+ Response:

			{
				"wishList": [{
					"name": "<Item Type>"
					"item" : [ <Item Details> ]
				}, {
					"name": "<Item Type>"
					"item" : [ <Item Details> ]
				} ...]
			}
---

###2. Add an item to wish list

+ **POST:** /WishList/add/:sessionId

+ Add an item to a wishlist.

+ Params:
 
	 	sessionId: <String>, the ID of the User.

+ Body:

     	itemId: <String>, the ID for the item.

+ Success: 201 (Success, edited)

+ Failure:
	 
	 + 464 (Item can not be wishlisted!)

---

###3. Delete an item from wish list

+ **POST:** /WishList/delete/:sessionId

+ Delete an item from a wishlist.

+ Params:

	 	sessionId: <String>, the ID of the User.

+ Body:

     	itemId: <String>, the ID for the item.

+ Success: 201 (Success, edited)

+ Failure:
	 + 465 (Item can not be deleted!)

##Part 6. Subscribe

###1. Add a new subscription.

+ **POST:**  /subscribeAdd/:sessionId

+ subscribe 1 type of furniture. (System can email new selling info under that category to user)  
	
+ Body:

		{
			"sessionId": <String>
			"Category" : <String>,
			"Sub-Category" : <String>,
			"notifyPeriod" : <String>
		}

+ Success: 201 (Success, add one more subscribe)

+ Failure:
	+ 404 (Invalid sessionId)

###2. Delete a subscription.

+ **POST:** /subscribeDelete/:sessionId

+ Given a user and a subscribe id, delete that subscribe.

+ Body:

		{
			"sessionId" : <String>,
			"subscribeId" : <int>,
		}

+ Success: 201 (Success, add one more subscribe)

+ Failure:

	+ 404 (Invalid userName)

##Part 7. Location

+ **GET:** /Category/Sub-Category/itemList/

+ Display the distance between user's current location and an item.

+ Body:

		{
			"sessionId" : <String>,
			"Category" : <String>,
			"Sub-category" : <String>,
			"latitude" : <String>,
			"longtitude" : <String>
		}

+ Response:

		{
			"result" : [{
				"itemId": "<int>,
			    "distanctToUser": "<float>"
			},{
				"itemId": "<int>,
			    "distanctToUser": "<float>"
			}
			...
			]
		}

	

Failure:

404 (request item has no valid location)

