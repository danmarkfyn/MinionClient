/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;
import minioning.common.data.EventData;
import minioning.common.data.Events;
import static minioning.common.data.Events.CREATEPLAYER;
import static minioning.common.data.Events.PLAY;
import minioning.common.data.Lists;
import minioning.common.data.LocalData;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakob
 */
public class LauncherLogicTest {
//    
//    public LauncherLogicTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of getDatagramSocket method, of class LauncherLogic.
//     */
//    @Test
//    public void testGetDatagramSocket() throws Exception {
//        System.out.println("getDatagramSocket");
//        DatagramSocket expResult = null;
//        DatagramSocket result = LauncherLogic.getDatagramSocket();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of nameCheck method, of class LauncherLogic.
     */
    @Test
    public void testNameCheck() {
        System.out.println("nameCheck");

        // Arrange
        // Create Objects
        LauncherLogic instance = new LauncherLogic();

        // Create variables
        String input1 = "New Player";
        String input2 = "Åse";
        String expResult = "NewPlayer";

        // Act
        String result1 = instance.nameCheck(input1);
        String result2 = instance.nameCheck(input2);

        // Assert
        assertEquals(expResult, result1);
        assertNotSame(expResult, result2);
    }
//
//    /**
//     * Test of CreatePlayer method, of class LauncherLogic.
//     */
    @Test
    public void testCreatePlayer() throws Exception {
        System.out.println("CreatePlayer");

        // Arrange
        
        String playerInfo = "TestPlayers";
        LauncherLogic LLInstance = new LauncherLogic();
 
        
        // Act
        
        LLInstance.CreatePlayer(playerInfo);
     
        // Assert
        
        assertTrue(Lists.getOutputList().isEmpty() == false);

    }
//
//    /**
//     * Test of accountQuery method, of class LauncherLogic.
//     */
//    @Test
//    public void testAccountQuery() throws Exception {
//        System.out.println("accountQuery");
//        Events event = null;
//        String username = "";
//        String password = "";
//        InetAddress IPAddress = null;
//        DatagramSocket clientSocket = null;
//        LauncherLogic instance = new LauncherLogic();
//        instance.accountQuery(event, username, password, IPAddress, clientSocket);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of promt method, of class LauncherLogic.
//     */
//    @Test
//    public void testPromt() {
//        System.out.println("promt");
//        
//        // Arrange
//        
//        String s1 = "Test1";
//        String s2 = "Test2";
//        LauncherLogic instance = new LauncherLogic();
//        
//        
//        // Act
//        instance.promt(s1, s2);
//        
//        // Assert
//        
//        
//        
//    }
//
//    /**
//     * Test of play method, of class LauncherLogic.
//     */

    @Test
    public void testPlay() throws Exception {
        System.out.println("play");

        // Arrange
        
        UUID testID = UUID.randomUUID();
        Events testEvent = PLAY;
        InetAddress IPAddress = null;
        DatagramSocket clientSocket = null;
        LauncherLogic LLInstance = new LauncherLogic();
 
        
        // Act
        
        LLInstance.play(testID, testEvent, IPAddress, clientSocket);
     
        // Assert
        
        assertTrue(Lists.getOutputList().isEmpty() == false);
    }

//    /**
//     * Test of setUUID method, of class LauncherLogic.
//     */
    @Test
    public void testSetUUID() {
        System.out.println("setUUID");

        // Arrange
        // Create Objects
        LauncherLogic LLInstance = new LauncherLogic();

        // Create Variables
        UUID testID = UUID.randomUUID();

        // Act
        LLInstance.setUUID(testID.toString());

        // Assert
        assertEquals(LocalData.getClientID(), testID);

    }
//    
}
