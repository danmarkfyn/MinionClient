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
import static minioning.common.data.Events.CREATEACCOUNT;
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

    /**
     * Test of nameCheck method, of class LauncherLogic.
     */
    @Test
    public void testNameCheck() {
        System.out.println("nameCheck");

        // Arrange
        // Create Objects
        LauncherLogic LLInstance = new LauncherLogic();

        // Create variables
        String input1 = "New Player";
        String input2 = "Åse";
        String expResult = "NewPlayer";

        // Act
        String result1 = LLInstance.nameCheck(input1);
        String result2 = LLInstance.nameCheck(input2);

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
        assertTrue(Lists.getOutputList().containsKey(CREATEPLAYER));
    }

    /**
     * Test of accountQuery method, of class LauncherLogic.
     */
    @Test
    public void testAccountQuery() throws Exception {
        System.out.println("accountQuery");

        // Arrange
        Events event = CREATEACCOUNT;
        String username = "player1";
        String password = "player1";

        LauncherLogic LLInstance = new LauncherLogic();

        // Act
        LLInstance.accountQuery(event, username, password);

        // Assert
        assertTrue(Lists.getOutputList().isEmpty() == false);
        assertTrue(Lists.getOutputList().containsKey(CREATEACCOUNT));

    }
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
        LauncherLogic LLInstance = new LauncherLogic();
        String testResult;

        // Act
        LLInstance.play(testID, testEvent);
        testResult = Lists.getOutputList().get(testEvent);

        // Assert
        assertTrue(Lists.getOutputList().isEmpty() == false);
        assertNotNull(testResult);
        assertEquals(testID + ";" + testEvent, testResult);

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
}
