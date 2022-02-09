package br.com.carlos.test;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DemoTestNG {

	private static ChromeDriver driver;

	@BeforeClass
	public static void setUpTest() {
		driver = new ChromeDriver();
//		String appUrl = "http://www.devmedia.com.br";
		String appUrl = "https://web.whatsapp.com/";
		
		driver.get(appUrl);
		driver.manage().window().maximize();
	}

	// Método que finaliza o teste, fechando a instância do WebDriver.
//	@AfterClass
//	public static void tearDownTest() {
//		driver.quit();
//	}

	// Testa título "DevMedia - Cursos, Tutoriais e Vídeos para Desenvolvedores".
	@Test
	public void testaTituloDaPagina() {
		assertEquals("DevMedia | Aprenda a Programar do Zero", driver.getTitle());
	}

	// Método que testa o login no site DevMedia.
	@Test
	public void testaLoginDevMedia() {

		// Instancia um novo objeto do tipo "WebElement", e passa como parâmetro
		// um elemento da tela cujo valor do atributo "name" seja igual a "usuario".
//		driver.findElementByClassName("link-login-devmedia").click();
		driver.findElementByClassName("_3m_Xw").click();

//		// Insere dados no elemento "usuario".
//		element.sendKeys("user@devmedia.com.br");
//
//		// Atribui ao objeto “element” o elemento de atributo "name" igual a "senha".
//		element = driver.findElement(By.name("senha"));
//
//		// Insere dados no elemento "senha".
//		element.sendKeys("123456");
//
//		// Clica no botão "OK" e submete os dados para concluir o login.
//		driver.findElement(By.id("imglogar")).click();
	}
}
