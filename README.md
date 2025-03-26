<html>
<h1>Showcase description / How2</h1>

<br/><br/><br/>
 
<h2>Run showcase app</h2>
 
 <ul>
 	<li>Run de.joergdev.mosy.showcase.drink.ordering.service.DrinkOrderingApplication</li>
 	<li>Open showcase UI: <a href="http://localhost:8080">http://localhost:8080</a></li>
 	<li>Show API result for get-all-orders: <a href="http://localhost:8080/api/orders">http://localhost:8080/api/orders</a></li>
 </ul>
   


<br/><br/><br/>
 
<h2>Init API mock</h2>
 
 <ul>
 	<li>Run MoSy backend + frontend
 		<ul>
 			<li>de.joergdev.mosy.backend.standalone.ApplicationMain</li>
 			<li>de.joergdev.mosy.frontend.SpringPrimeFacesApplication</li>
 		</ul>
 	</li>
 	<li>Create interface "showcase-drinkordering"
 		<ul>
 			<li>Open MoSy UI: <a href="http://localhost:8087/login.xhtml">http://localhost:8087/login.xhtml</a></li>
 			<li>Type: REST</li>
 			<li>Service-path: api/orders</li>
 			<li>Routing-url: http://localhost:8080/api/orders</li>
 			<li>create method get-all
 				<ul>
 					<li>Mock + route active</li>
 				</ul>
 			</li>
 		</ul>
 	</li>
 	<li>Show API result for get-all-orders routed from MoSy to showcase app: <a href="http://localhost:3911/mosy/api/v_5_0/mock-services/rest/api/orders">http://localhost:3911/mosy/api/v_5_0/mock-services/rest/api/orders</a></li>
 	<li>Create recordSession</li>
 </ul>
    


<br/><br/><br/>
 
<h2>Run showcase app with API record & mock</h2>
 
 <ul>
 	<li>Open showcase UI: <a href="http://localhost:8080">http://localhost:8080</a></li>
 	<li>Activate mock and set recordSessionId</li>
 	<li>Refresh orders</li>
 	<li>Check for record in MoSy
 		<ul>
 			<li><a href="http://localhost:8087/login.xhtml">http://localhost:8087/login.xhtml</a></li>
 		</ul>
 	</li>
 	<li>Set record as mockdata and change return value</li>
 	<li>Refresh orders => mocked data is loaded</li>
 </ul>

<br/><br/><br/>

</html>