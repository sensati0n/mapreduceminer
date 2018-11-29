package mapreduceminer;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import junit.framework.Assert;
import mapreduceminer.ApiController;
import mapreduceminer.Application;
import mapreduceminer.service.HospitalLoader;
import mapreduceminer.service.XesLoader;

@SpringBootTest
public class XesLoaderTest {
    

private String xesMock =  "<?xml version='1.0' encoding='UTF-8'?><eventLog><trace>        <event>            <string key='task' value='a'/>            <string key='resource' value='x'/>        </event>        <event>            <string key='task' value='c'/>            <string key='resource' value='z'/>        </event>        <event>            <string key='task' value='b'/>            <string key='resource' value='y'/>        </event>        <event>            <string key='task' value='b'/>            <string key='resource' value='x'/>        </event>        <event>            <string key='task' value='d'/>            <string key='resource' value='z'/>        </event>        <event>            <string key='task' value='b'/>            <string key='resource' value='y'/>        </event>        <event>            <string key='task' value='a'/>            <string key='resource' value='x'/>        </event>    </trace>    <trace>        <event>            <string key='task' value='a'/>            <string key='resource' value='x'/>        </event>        <event>            <string key='task' value='b'/>            <string key='resource' value='x'/>        </event>        <event>            <string key='task' value='b'/>            <string key='resource' value='y'/>        </event>        <event>            <string key='task' value='c'/>            <string key='resource' value='x'/>        </event>    </trace>	<trace>        <event>            <string key='task' value='a'/>            <string key='resource' value='y'/>        </event>        <event>            <string key='task' value='c'/>            <string key='resource' value='x'/>        </event>        <event>            <string key='task' value='d'/>            <string key='resource' value='y'/>        </event>    </trace>	<number>100</number></eventLog>";


@Test
public void testLoadHospital() throws Exception {

    HospitalLoader xesLoader = new HospitalLoader();
    xesLoader.loadXes(xesMock);
}
}