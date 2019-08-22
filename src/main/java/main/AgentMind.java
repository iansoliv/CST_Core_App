package main;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import br.unicamp.cst.util.MindViewer;
import codelets.behaviour.EatClosestApple;
import codelets.behaviour.Forage;
import codelets.behaviour.GoToClosestApple;
import codelets.motor.HandsActionCodelet;
import codelets.motor.LegsActionCodelet;
import codelets.perceptual.AppleDetector;
import codelets.perceptual.ClosestAppleDetector;
import codelets.sensory.InnerSense;
import codelets.sensory.Vision;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import memory_objects.CreatureInnerSense;
import ws3dproxy.model.Thing;

public class AgentMind extends Mind {

	private static final int creatureBasicSpeed = 1;
	private static final int reachDistance = 50;

	public AgentMind( Environment env )
	{
		super();
		
		// Declare Memory Objects
		MemoryObject legsMO;
		MemoryObject handsMO;
		MemoryObject visionMO;
		MemoryObject innerSenseMO;
		MemoryObject closestAppleMO;
		MemoryObject knownApplesMO;

		//Initialize Memory Objects
		legsMO = createMemoryObject( "LEGS", "" );
		handsMO = createMemoryObject( "HANDS", "" );
		List<Thing> vision_list = Collections.synchronizedList( new ArrayList<>() );
		visionMO = createMemoryObject( "VISION", vision_list );
		CreatureInnerSense cis = new CreatureInnerSense();
		innerSenseMO = createMemoryObject( "INNER", cis );
		Thing closestApple = null;
		closestAppleMO = createMemoryObject( "CLOSEST_APPLE", closestApple );
		List<Thing> knownApples = Collections.synchronizedList( new ArrayList<>() );
		knownApplesMO = createMemoryObject( "KNOWN_APPLES", knownApples );

//		// Create and Populate MindViewer
//		MindView mv = new MindView( "MindView" );
//		mv.addMO( knownApplesMO );
//		mv.addMO( visionMO );
//		mv.addMO( closestAppleMO );
//		mv.addMO( innerSenseMO );
//		mv.addMO( handsMO );
//		mv.addMO( legsMO );
//		mv.StartTimer();
//		mv.setVisible( true );
		
		/*
		 * Create all codelets
		*/
		
		// initialize the list of codelets to show through a MindViewer instance.
		List<Codelet> codeletsToShow = new ArrayList<>();
		
		// Create Sensor Codelets	
		Codelet vision = new Vision( env.c );
		vision.addOutput( visionMO );
		insertCodelet( vision );				// Insert the vision sensor codelet into the Mind (super)
		codeletsToShow.add( vision );		// show this codelet

		Codelet innerSense = new InnerSense( env.c );
		innerSense.addOutput( innerSenseMO );
		insertCodelet( innerSense );			//A sensor for the inner state of the creature
		codeletsToShow.add( innerSense );		// show this codelet

		// Create Actuator Codelets
		Codelet legs = new LegsActionCodelet( env.c );
		legs.addInput( legsMO );
		insertCodelet( legs );
		codeletsToShow.add( legs );			// show this codelet

		Codelet hands = new HandsActionCodelet( env.c );
		hands.addInput( handsMO );
		insertCodelet( hands );
		codeletsToShow.add( hands );		// show this codelet

		// Create Perception Codelets
		Codelet ad = new AppleDetector();
		ad.addInput( visionMO );
		ad.addOutput( knownApplesMO );
		insertCodelet( ad );
		codeletsToShow.add( ad );			// show this codelet

		Codelet closestAppleDetector = new ClosestAppleDetector();
		closestAppleDetector.addInput( knownApplesMO );
		closestAppleDetector.addInput( innerSenseMO );
		closestAppleDetector.addOutput( closestAppleMO );
		insertCodelet( closestAppleDetector );
		codeletsToShow.add( closestAppleDetector );			// show this codelet

		// Create Behavior Codelets
		Codelet goToClosestApple = new GoToClosestApple( creatureBasicSpeed, reachDistance );
		goToClosestApple.addInput( closestAppleMO );
		goToClosestApple.addInput( innerSenseMO );
		goToClosestApple.addOutput( legsMO );
		insertCodelet( goToClosestApple );
		codeletsToShow.add( goToClosestApple );			// show this codelet

		Codelet eatApple = new EatClosestApple( reachDistance );
		eatApple.addInput( closestAppleMO );
		eatApple.addInput( innerSenseMO );
		eatApple.addOutput( handsMO );
		eatApple.addOutput( knownApplesMO );
		insertCodelet( eatApple );
		codeletsToShow.add( eatApple );					// show this codelet

		Codelet forage = new Forage();
		forage.addInput( knownApplesMO );
		forage.addOutput( legsMO );
		insertCodelet( forage );
		codeletsToShow.add( forage );					// show this codelet
		
		
		
				
		MindViewer mindViewer = new MindViewer(this, "CST Mind Viewer", codeletsToShow);
		mindViewer.setVisible(true);
		
		// Start Cognitive Cycle
		super.start();
	}
}
