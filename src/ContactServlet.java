
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "ContactServlet", urlPatterns = { "/contacts" })
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	private Gson gson;

	public ContactServlet() {
		super();
		// TODO Auto-generated constructor stub
		gson = new GsonBuilder().create();
	}

	public void sendJsonObject(HttpServletResponse response, Contact contact) {
		response.setContentType("text/html");
		JsonObject jsonObject = gson.toJsonTree(contact).getAsJsonObject();
		try {
			response.getWriter().print(jsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendJsonArray(HttpServletResponse response, Object object) throws IOException {
		response.setContentType("text/html");
		JsonArray jsonArray = gson.toJsonTree(object).getAsJsonArray();
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("contacts", jsonArray);
		response.getWriter().print(jsonObject.toString());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
//		Service.adding_contacts_for_test();
		sendJsonArray(response, Service.getContactList());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		Contact contact = gson.fromJson(json, Contact.class);
		boolean result;
		result = new Service().addName(contact.getLogin(), contact.getID(), contact.getName(),
				contact.getAdditionalInformation(), contact.getAddress());
		if(result)
			sendJsonObject(response,contact);
		else
			response.getWriter().print("false");
		
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		response.setCharacterEncoding("UTF-8");
		String ID = "";
		if(br != null)
			ID = br.readLine();
		boolean result = new Service().deleteName(ID);
		response.getWriter().print(result);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		Contact contact = gson.fromJson(json, Contact.class);
		boolean result = false;
		if(contact.getName()!=null || contact.getLogin()!=null) {
			result = new Service().updateContact(contact.getID(),contact.getLogin(),contact.getName());
		}
		if(contact.getAdditionalInformation()!=null)
			result = new Service().updateAdditionalInformation(contact.getID(),contact.getAdditionalInformation());
		if(contact.getAddress()!=null) 
			result = new Service().updateAddress(contact.getID(),contact.getAddress());
		response.getWriter().print(Boolean.toString(result));
	}


}
