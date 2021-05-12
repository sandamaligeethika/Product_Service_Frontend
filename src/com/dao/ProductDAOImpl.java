package com.dao;

import java.sql.*;
import java.util.List;

import com.dbConnection.DBConnection;
import com.models.Product;

public class ProductDAOImpl {

	// declaring a DBConnection type object
		DBConnection db = new DBConnection();

		List<Product> products;

		//insert a new product by passing a Product type object as the parameter
		public String createProduct(Product p1){
			
			//13-05
			String output = "";
			try {
				// establishing a connection
				Connection connection = db.connect();

				// query to insert a product to the product table in the database
				String insertProduct = "INSERT INTO `product`(`product_id`, `product_name`, `date`, `price`,`resId`) VALUES (?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(insertProduct);
			
				ps.setInt(1, p1.getProductId());
				ps.setString(2, p1.getName());
				ps.setDate(3, p1.getDate());
				ps.setDouble(4, p1.getPrice());
				ps.setString(5, p1.getResId());

				ps.execute();
				
				// close the connection
				connection.close();
				
				//13-05
				String newItems = listProducts();
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			} catch (SQLException e) {
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
				System.err.println(e.getMessage());
			}
			// return the product type object
			return output;
		}

		// retrieve the list of all products
		public String listProducts() {
			
			String output = "";

			try {
				Connection connection = db.connect();
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Product ID</th>"
				+ "<th>Product Name</th><th>Date</th><th>Price</th>"
				+ "<th>ResearcherId</th>"
				+ "<th>Item Description</th>"
				+ "<th>Update</th><th>Remove</th></tr>";

				// query to retrieve all the products from the database
				String prodList = "select * from product";

				Statement st = connection.createStatement();
				
				ResultSet rs = st.executeQuery(prodList);

				while (rs.next()) {
//					Product product = new Product();
//					product.setId(rs.getInt(1));
//					product.setProductId(rs.getInt(2));
//					product.setName(rs.getString(3));
//					product.setDate(rs.getDate(4));
//					product.setPrice(rs.getDouble(5));
//					product.setResId(rs.getString(6));
					
					int Id = rs.getInt(1);
					int productId = rs.getInt(2);
					String name = rs.getString(3);
					Date date = rs.getDate(4);
					Double price = rs.getDouble(5);
					String resId = rs.getString(6);
				
					// Add into the html table
					output += "<tr><td>" + productId + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + date + "</td>";
					output += "<td>" + price + "</td>";
					output += "<td>" + resId + "</td>";
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' "
					+ "class='btnUpdate btn btn-secondary' data-itemid='" + Id + "'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' "
					+ "class='btnRemove btn btn-danger' data-itemid='" + Id + "'></td></tr>";
					}
				
					connection.close();
					// Complete the html table
					output += "</table>";
		
			} catch (SQLException e) {
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
			}

			return output;
		}

//		// get a particular product by passing the product id
//		public Product getProductById(int id) throws SQLException {
//
//			// declare a product type object
//			Product product = new Product();
//
//			//the connection object to the database
//			Connection connection = db.connect();
//
//			// query to get a particular product by the product id
//			String productList = "select * from product where product_id = '" + id + "'";
//
//			Statement st = connection.createStatement();
//			ResultSet rs = st.executeQuery(productList);
//
//			while (rs.next()) {
//
//				product.setId(rs.getInt(1));
//				product.setProductId(rs.getInt(2));
//				product.setName(rs.getString(3));
//				product.setDate(rs.getDate(4));
//				product.setPrice(rs.getDouble(5));
//				product.setResId(rs.getString(6));
//
//			}
//
//			return product;
//		}

		// update a product by passing a product type object as the parameter
		public String updateProduct(Product product){
			String output = "";

			try {
				
				Connection connection = db.connect();

				// query to update the product details by setting the values respectively
				String updateProduct = "update product set product_id = '" + product.getProductId() + "', product_name ='"
						+ product.getName() + "', date = '" + product.getDate() + "', price = '" + product.getPrice()
						+ "', resId = '" + product.getResId() + "' where id = '" + product.getId() + "'";

				PreparedStatement ps = connection.prepareStatement(updateProduct);
				
				ps.executeUpdate();

				// close the connection
				connection.close();
				
				String newItems = listProducts();
				output = "{\"status\":\"success\", \"data\": \"" +
				newItems + "\"}";
			} catch (SQLException e) {
				output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}

		// delete a product by passing the id
		public String deleteProduct(int id){
			
			String output = "";
			
			try {
				Connection connection = db.connect();

				// query to delete a product
				String deleteProduct = "delete from product where id = '" + id + "'";
				PreparedStatement ps = connection.prepareStatement(deleteProduct);
				
				ps.execute();
				// close the connection
				connection.close();
				
				String newItems = listProducts();
				output = "{\"status\":\"success\", \"data\": \"" +
				newItems + "\"}";
			} catch (SQLException e) {
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
				System.err.println(e.getMessage());
			}
			return output;

		}
}
