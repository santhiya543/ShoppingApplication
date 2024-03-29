package utils;

public class Query {
	public static final String getAllProducts="SELECT p.product_id,p.product_name,p.price,p.image,o.discountPercentage,o.validityPeriod FROM Products p JOIN Offers o ON p.offer_id = o.offers_id";
    public static final String getProductDetail="SELECT p.product_id,p.product_name,p.price,p.image,p.description,sc.subCategory_name, c.category_name, s.user_name AS seller_name, b.brand_name, b.brandDescription, o.discountPercentage, o.validityPeriod, st.quantity FROM Products p JOIN SubCategories sc ON p.subCategory_id = sc.subCategory_id JOIN Categories c ON sc.category_id = c.category_id JOIN Users s ON p.seller_id = s.mailID JOIN Brands b ON p.brand_id = b.brand_id JOIN Offers o ON p.offer_id = o.offers_id JOIN Stock st ON p.product_id = st.product_id where p.product_id like ?";
    public static final String addToWishList="INSERT INTO WishLists (date, product_id,customer_id) VALUES (?, ?,?);";
    public static final String getPasswordByMailID = "SELECT passwd FROM Login WHERE mailID like ?;";
    public static String CreateAccount ="INSERT INTO Users (mailID, user_name) VALUES (?, ?);";
    public static String InsertCredentials = "INSERT INTO Login (mailID, passwd) VALUES (?, ?);";
    public static String DeleteSession = "DELETE FROM Session where sessionID like ?";
    public static String CreateNewSession = "INSERT INTO Session (sessionID, mailID, loggedTime) VALUES (?, ?, ?);";
}
