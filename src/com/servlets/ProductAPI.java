package com.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ProductDAOImpl;
import com.models.Product;

/**
 * Servlet implementation class ProductAPI
 */
@WebServlet("/ProductAPI")
public class ProductAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Product productObj;
	private static ProductDAOImpl productDaoObj;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductAPI() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		productObj = new Product();
		productDaoObj = new ProductDAOImpl();
		
		productObj.setProductId(Integer.parseInt(request.getParameter("productId")));
		productObj.setName(request.getParameter("productName"));
		productObj.setDate(Date.valueOf(request.getParameter("productDate")));
		productObj.setPrice(Double.parseDouble(request.getParameter("productPrice")));
		productObj.setResId(request.getParameter("researcherId"));
		
		String output = productDaoObj.createProduct(productObj);
		
		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		productObj = new Product();
		productDaoObj = new ProductDAOImpl();
		
		Map parameters = getParasMap(request);
		
		productObj.setId(Integer.parseInt(parameters.get("hidItemIDSave").toString()));
		productObj.setProductId(Integer.parseInt(parameters.get("productId").toString()));
		productObj.setName(parameters.get("productName").toString());
		productObj.setDate(Date.valueOf(parameters.get("productDate").toString()));
		productObj.setPrice(Double.parseDouble(parameters.get("productPrice").toString()));
		productObj.setResId(parameters.get("researcherId").toString());
		
		String output = productDaoObj.updateProduct(productObj);
		
		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		productDaoObj = new ProductDAOImpl();
		
		Map parameters = getParasMap(request);
		
		String output = productDaoObj.deleteProduct(Integer.parseInt(parameters.get("id").toString()));
		response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
			scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}
}
