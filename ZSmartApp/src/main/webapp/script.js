function load() {
    function checkLoginStatus() {
        var loggedInCookie = getCookie("sessionID");
        if (loggedInCookie) {
            document.getElementsByClassName('logoutButton')[0].style.display = 'block';
            document.getElementsByClassName('loginButton')[0].style.display = 'none';
            document.getElementById('wish').style.display = 'block';
            document.getElementById('cart').style.display = 'block';
        } else {
            document.getElementsByClassName('logoutButton')[0].style.display = 'none';
            document.getElementsByClassName('loginButton')[0].style.display = 'block';
            document.getElementById('wish').style.display = 'none';
            document.getElementById('cart').style.display = 'none';
        }
}
    checkLoginStatus();
};


function getCookie(cookieName) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim().split('=');
        if (cookie[0] === cookieName) {
            return cookie[1];
        }
    }
    return null;
}

function getUserIdFromCookie() {
    var cookies = document.cookie.split("; ");
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].split("=");
        if (cookie[0] === "userID") {
            return cookie[1];
        }
    }
    return null;
}

function signIn() {
    var log = {};
    log["username"] = document.getElementById("userName").value;
    log["password"] = document.getElementById("password").value;
    console.log(log.username + "=============" + log.password);

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var responseCode = JSON.parse(this.responseText);
            
            console.log("Response from server:", responseCode);
            console.log("responseCode"+ responseCode);
            console.log("responseCode" + responseCode.statuscode == 200);
            if (responseCode && responseCode.statuscode == 200) {
                console.log("into cookie");
                document.cookie = "sessionID=" + responseCode.sessionID;
                document.cookie = "userID=" + responseCode.mailID;
                window.location.href = "index.html";
            } else {
                alert("Error: " + responseCode.message);
            }
        } else {
            alert("Unknown response code: " + this.status);
        }
    };
    xhr.open("POST", "http://localhost:8080/ZSmartApp/v1/login?", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("mailID=" + log.username + "&passwd=" + log.password);
}

function sendSignupRequest() {	
	 var log = {};
    log["username"] = document.getElementById("username").value;
    log["Password"] = document.getElementById("password").value;
    log["email"] = document.getElementById("email").value;
    
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        var responseData = JSON.parse(this.responseText); 
        console.log("Response from server:", responseData);
        if (responseData && (responseData.statuscode==200)) {
			console.log("into cookie");
			document.cookie = "sessionID"+responseData.sessionID;
			document.cookie = "userID"+responseData.mailID;
          	window.location.href = "index.html"; 
        } else {
            alert("Something went wrong");
        }
    }
    };
    xhr.open("POST", "http://localhost:8080/ZSmartApp/v1/signup?", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    console.log("mail"+log.email+"user"+log.username+"pass"+log.Password)
    xhr.send("userName=" + log.username + "&passwd=" + log.Password+"&mailID="+log.email);
}

function logout(){
	console.log("logout in");
    var xhr = new XMLHttpRequest();   
    xhr.onreadystatechange = function() {
            if (xhr.status === 200 && xhr.readyState == 4) {
				console.log("status success");
				document.getElementById('logoutButton').style.display = 'none';
				console.log("status success1");
                document.getElementById('loginButton').style.display = 'block';
                console.log("status success2");
                document.getElementById('wish').style.display = 'none';
                console.log("status success3");
                document.getElementById('cart').style.display = 'none';
                console.log("status success4");
                window.location.href = "index.html"; 
            } else {
                console.log('Logout failed: ' + xhr.responseText);
            }
    };
	
	xhr.open("GET", "http://localhost:8080/ZSmartApp/v1/logout", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send();
}


function getAllProducts() {
	console.log("into the product");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/ZSmartApp/v1/GetAllProduct", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("mode", "no-cros");
    xhr.onreadystatechange = function() {
		console.log("into the product "+xhr.readyState);
		console.log("into the product"+xhr.status);
        if (xhr.readyState == 4 && xhr.status == 200) {
            var products = JSON.parse(xhr.responseText).data;
            console.log(products);
            displayProducts(products);
        }
    };
    xhr.send();
    console.log("out the product");
}

function getProductsByCategory(categoryName) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "getProductsByCategory?categoryName=" + categoryName, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var products = JSON.parse(xhr.responseText);
            console.log(products);
            displayProducts(products);
            
        }
    };
    xhr.send();
}

function getProduct(productID) {
	console.log("into the product");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/ZSmartApp/v1/GetProductDetails?productID="+productID, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
		console.log("into the product "+xhr.readyState);
		console.log("into the product"+xhr.status);
        if (xhr.readyState == 4 && xhr.status == 200) {
            var products =xhr.responseText;
            console.log(products);
            localStorage.setItem("product",products);
            console.log(products);
            window.location.href = "product.html";
            //displayProduct();
        }
    };
    xhr.send();
    console.log("out the product");
}

function displayProducts(products) {
    var container = document.getElementById("productContainer");
    console.log(container);
    container.innerHTML = "";

    products.forEach(function(product) {
        var productDiv = document.createElement("div");
        productDiv.classList.add("product");
        console.log(product.ID+"product");
         productDiv.setAttribute("productID",product.ID);
        /*productDiv.setAttribute("onclick", "getProduct('"+product.ID+"')");*/
		productDiv.addEventListener("click", function() {
            getProduct(product.ID);
        });
        
        
		var productImage = document.createElement("img");
        productImage.src = product.image;
        productImage.classList.add("productimage");
        
        var productName = document.createElement("h3");
        productName.textContent = product.name;
        productName.classList.add("productname");
        
        productDiv.setAttribute("productID", product.ID);

        var productPrice = document.createElement("p");
        productPrice.textContent = "Price: $" + product.price;
        productPrice.classList.add("price");
        
        var discountPercentage = document.createElement("p");
        discountPercentage.textContent = "DiscountPercentage: "+product.offer+"%";
        discountPercentage.classList.add("discountPercentage");
        
        var validity = document.createElement("p");
        validity.textContent = "validityPeriod: "+product.validityPeriod;
        validity.classList.add("validityPeriod");

	    productDiv.appendChild(productImage);
		productDiv.appendChild(productName);
        productDiv.appendChild(productPrice);
        productDiv.appendChild(discountPercentage);
        productDiv.appendChild(validity);

        container.appendChild(productDiv);
    });
}

function displayProduct() {
    console.log("into single product");
    var container = document.getElementById("productPageContainer");
    console.log(container);
    container.innerHTML = "";
    var data=localStorage.getItem("product");
    console.log(data);
    var product=JSON.parse(data);
    product=product.data[0];
   console.log("product");
    console.log(product);

    var productDiv = document.createElement("div");
    productDiv.classList.add("productPageWholeContainer");
    console.log(product.ID + "product");

    var productImgDiv = document.createElement("div");
    productImgDiv.classList.add("productImgDiv");

    var productDetailsDiv = document.createElement("div");
    productDetailsDiv.classList.add("productDetailsDiv");

    var productImage = document.createElement("img");
    productImage.src = product.image;
    productImage.classList.add("productDetailsimage");

    var productName = document.createElement("h3");
    productName.textContent = product.name;
    productName.classList.add("productDetailsname");

    var productPrice = document.createElement("p");
    productPrice.textContent = "Price: $" + product.price;
    productPrice.classList.add("productDetailsprice");
    productPrice.classList.add("productDetailsSize");

    var discountPercentage = document.createElement("p");
    discountPercentage.textContent = "Discount Percentage: " + product.offer + "%";
    discountPercentage.classList.add("productDetailsPercentage");
    discountPercentage.classList.add("productDetailsSize");

    var validity = document.createElement("p");
    validity.textContent = "Validity Period: " + product.validityPeriod;
    validity.classList.add("productDetailsvalidityPeriod");
    validity.classList.add("productDetailsSize");

    var productDescription = document.createElement("p");
    productDescription.textContent = "Description: " + product.description;
    productDescription.classList.add("productDetailsdescription");
    productDescription.classList.add("productDetailsSize");

    var productBrand = document.createElement("p");
    productBrand.textContent = "Brand: " + product.brand;
    productBrand.classList.add("productDetailsbrand");
    productBrand.classList.add("productDetailsSize");
    
    var productButtons = document.createElement("div");
    productButtons.classList.add("productButtons");
    
     var productCartButton = document.createElement("button");
    productCartButton.textContent = "Add To Cart";
    productCartButton.setAttribute("id","cartButton");
    
    var productOrderButton = document.createElement("button");
    productOrderButton.textContent = "Buy Now";
    productOrderButton.setAttribute("id","orderButton");
    
    var productWishButton = document.createElement("div");
	productWishButton.setAttribute("id", "wishButton");
	productWishButton.innerHTML = '<i class="fa-regular fa-heart heart1" style="color: black;font-size: 1.5vw;"></i>'+ '<i class="fa-solid fa-heart heart2" style="color: black;font-size: 1.5vw; display:none"></i>';
	productWishButton.addEventListener("click", function() {
            toggleWish(product.ID);
    });

    productImgDiv.appendChild(productImage);

    productDetailsDiv.appendChild(productName);
    productDetailsDiv.appendChild(productPrice);
    productDetailsDiv.appendChild(discountPercentage);
    productDetailsDiv.appendChild(validity);
    productDetailsDiv.appendChild(productDescription);
    productDetailsDiv.appendChild(productBrand);
    productDetailsDiv.appendChild(productButtons);
    
    
    productButtons.appendChild(productCartButton);
    productButtons.appendChild(productOrderButton);
    productButtons.appendChild(productWishButton);
    
    productDiv.appendChild(productImgDiv);
    productDiv.appendChild(productDetailsDiv);

    container.appendChild(productDiv);
}



function toggleWish(productID){
    var heart1 = document.getElementsByClassName('heart1')[0];
    var heart2 = document.getElementsByClassName('heart2')[0];
    if (heart1.style.display == "block"){
        heart1.style.display = 'none';
        heart2.style.display = 'block';
        addToWishList(productID);
    } else {
        heart2.style.display = 'none';
        heart1.style.display = 'block';
        removeToWishList(productID);
    }
}



function addToWishList(productID) {
	console.log("in wish");
    var userId = getUserIdFromCookie("userId");

    if (userId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/ZSmartApp/addToWishList", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {								
                console.log("xhr"+xhr.responseText);
            }
        };
        console.log("userID",userId);
        xhr.send("productID=" + productID + "&mail=" + userId);
    } else {
        console.log("User is not logged in");
    }
}

function viewWishList() {
	console.log("into the product");
	var name = getCookie("name");
	console.log("wishList"+name);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/ZSmartAppApp/showWishList?userName="+name, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
		console.log("into the product "+xhr.readyState);
		console.log("into the product"+xhr.status);
        if (xhr.readyState == 4 && xhr.status == 200) {
			console.log("ok!!!!!!!!!!!!!!!!!!");
            var products = JSON.parse(xhr.responseText).data;  
            console.log(products.length); 
           if (products.length > 0) {
    			displayWishProduct(products);
			}
           else{
				console.log("empty");
				empty("Wish List");
			}
        }
    };
    xhr.send();
    console.log("out the product");
}

function displayWishProduct(products) {
    var container = document.getElementById("wishContainer");
    container.innerHTML = "";
	console.log(products[0].discountPercentage);
    products.forEach(function(product) {
		console.log("products"+product);
        var productDiv = document.createElement("div");
        productDiv.classList.add("product");
		
		var productImage = document.createElement("img");
        productImage.src = product.productImage;
        productImage.classList.add("productimage");
        
        var productName = document.createElement("h3");
        productName.textContent = product.productName;
        productName.classList.add("productname");
        productName.setAttribute("productname", product.name);

        var productPrice = document.createElement("p");
        productPrice.textContent = "Price: $" + product.productPrice;
        productPrice.classList.add("pice");

        var productDescription = document.createElement("p");
        productDescription.textContent = "Description: "+product.productDescription;
        productDescription.classList.add("description");
        
        var discountPercentage = document.createElement("p");
        discountPercentage.textContent = "DiscountPercentage: "+product.discountPercentage+"%";
        discountPercentage.classList.add("discountPercentage");
        
        var brandName = document.createElement("p");
        brandName.textContent = "BrandName: "+product.brandName;
        brandName.classList.add("brandName");
        
         var date = document.createElement("p");
        date.textContent = "Date: "+product.date;
        date.classList.add("date");

	    productDiv.appendChild(productImage);
		productDiv.appendChild(productName);
        productDiv.appendChild(productPrice);
        productDiv.appendChild(productDescription);
        productDiv.appendChild(productPrice);
        productDiv.appendChild(discountPercentage);
        productDiv.appendChild(brandName);
        productDiv.appendChild(date);
        
        var cart = document.createElement("button");
		cart.textContent = "Add to Cart";
		cart.id="cartButton";
		//cart.setAttribute("onclick", "addCart()"); 
        
        var order=document.createElement("button");
        order.textContent = "BuyNow";
        order.id = "orderButton";
        
        var wish = document.createElement("div");
        wish.innerHTML = '<i class="fa-regular fa-heart heart1" style="font-size: 2vw;display:block";color:black;></i>'+'<i class="fa-solid fa-heart heart2" style="font-size: 2vw; display:none;color:red"></i>';
        wish.id = "wishButton";
        wish.setAttribute("onclick", "toggleWish('" + product.name + "')");

        
        productDiv.appendChild(cart);
        productDiv.appendChild(order);
        productDiv.appendChild(wish);

        container.appendChild(productDiv);
    });
    
}

function empty(name){
	var container = document.getElementById("wishContainer");
	container.innerHTML = "";
	   /* var emptyDiv = document.createElement("div");
        emptyDiv.classList.add("emptyDiv");*/
		
		var content = document.createElement("h2");
        content.classList.add("content");
        content.textContent = "No Items in your "+name+"!!!";
       container.appendChild(content);
}


function removeToWishList(productID) {
	console.log("removewish");
    var userId = getUserIdFromCookie("userId");

    if (userId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/ZSmartApp/removeToWishList", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {								
                console.log("xhr"+xhr.responseText);
            }
        };
         console.log("userID",userId);
        xhr.send("productID=" + productID + "&mail=" + userId);
    } else {
        console.log("User is not logged in");
    }
}

function addToCart(productId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'AddToCartServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            console.log('Item added to cart successfully.');
        } else {
            console.error('Failed to add item to cart.');
        }
    };
    xhr.onerror = function() {
        console.error('Error occurred while making the request.');
    };
    xhr.send(JSON.stringify({ productId: productId }));
}

function addToCart(productName,quantity) {

    var userId = getUserIdFromCookie(); 

    if (userId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/ZSmartApp/addToCart", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                console.log(xhr.responseText);
            }
        };
        xhr.send("productName=" + productName+ "&userId=" + userId+"&quantity="+quantity);
    } else {
        console.log("User is not logged in");
    }
}

function viewCart() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'ViewCartServlet', true);
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            console.log(xhr.responseText);
        } else {
            console.error('Failed to view cart.');
        }
    };
    xhr.onerror = function() {
        console.error('Error occurred while making the request.');
    };
    xhr.send();
}


function addProduct() {
    var productName = document.getElementById("productName").value;
    var price = document.getElementById("price").value;
    var description = document.getElementById("description").value;
    var image = document.getElementById("image").value;
    var subCategory = document.getElementById("subCategory").value;
    var seller = document.getElementById("seller").value;
    var brand = document.getElementById("brand").value;
    var discountPercentage = document.getElementById("discountPercentage").value;
    var validityPeriod = document.getElementById("validityPeriod").value;

    var data = {
        productName: productName,
        price: price,
        description: description,
        image: image,
        subCategory: subCategory,
        seller: seller,
        brand: brand,
        discountPercentage: discountPercentage,
        validityPeriod: validityPeriod
    };


    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/ZSmartApp/addProduct", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            alert(response);
        }
    };
    xhr.send(JSON.stringify(data));
}


function updateStock(productName, newStock) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "updateStock", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
        }
    };
    xhr.send("productName=" + productName + "&newStock=" + newStock);
}

function provideOffer(productName, discountPercentage, validityPeriod) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "provideOffer", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
        }
    };
    xhr.send("productName=" + productName + "&discountPercentage=" + discountPercentage + "&validityPeriod=" + validityPeriod);
}


/*///////////////////////////////////////////*/
function toggleSignup() {
    document.getElementById("loginPage").style.display = "none";
    document.getElementById("signupPage").style.display = "block";
}

function toggleLogin() {
    document.getElementById("signupPage").style.display = "none";
    document.getElementById("loginPage").style.display = "block";
}

function clearBorder(idName, idValue) {
    if (idValue == '') document.getElementById(idName).style.borderColor = '#d40000';
    else document.getElementById(idName).style.borderColor = 'green';
}

function hideAndSeek(n) {
    document.getElementById(`eyeOpen${n}`).classList.toggle("hide");
    document.getElementById(`eyeClose${n}`).classList.toggle("hide");
    let tempEle = (n == 1) ? newPassword : password;
    if (document.getElementById(`eyeOpen${n}`).className.includes("hide")) tempEle.type = 'text';
    else tempEle.type = 'password';
}

