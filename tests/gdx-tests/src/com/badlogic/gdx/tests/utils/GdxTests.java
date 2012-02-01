/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.badlogic.gdx.tests.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.tests.*;
import com.badlogic.gdx.tests.examples.MoveSpriteExample;
import com.badlogic.gdx.tests.gles2.HelloTriangle;
import com.badlogic.gdx.tests.gles2.SimpleVertexShader;

/**
 * List of GdxTest classes. To be used by the test launchers. If you write your
 * own test, add it in here!
 * 
 * @author badlogicgames@gmail.com
 */
public class GdxTests {
	public static final Class[] tests = { AnimationTest.class,
			AccelerometerTest.class, ActionTest.class,
			ActionSequenceTest.class, GroupTest.class, AlphaTest.class,
			AtlasIssueTest.class, AssetManagerTest.class,
			FilterPerformanceTest.class, AudioDeviceTest.class,
			AudioRecorderTest.class, BitmapFontAlignmentTest.class,
			BitmapFontFlipTest.class, GroupCullingTest.class,
			GestureDetectorTest.class, LabelTest.class, BitmapFontTest.class,
			BlitTest.class, TableTest.class, BobTest.class,
			ImageScaleTest.class, Box2DInitialOverlapTest.class,
			Box2DTest.class, InterpolationTest.class,
			Box2DTestCollection.class, BufferUtilsTest.class, ImageTest.class,
			CompassTest.class, ComplexActionTest.class, CullTest.class,
			DeltaTimeTest.class, EdgeDetectionTest.class, ETC1Test.class,
			ExitTest.class, FilesTest.class, FlickScrollPaneTest.class,
			FloatTest.class, FrameBufferTest.class,
			FramebufferToTextureTest.class, FrustumTest.class,
			FullscreenTest.class, Gdx2DTest.class, GroupFadeTest.class,
			ImmediateModeRendererTest.class,
			ImmediateModeRendererAlphaTest.class,
			IndexBufferObjectClassTest.class,
			IndexBufferObjectShaderTest.class, InputTest.class,
			IntegerBitmapFontTest.class, InverseKinematicsTest.class,
			IsoCamTest.class, IsometricTileTest.class, KinematicBodyTest.class,
			LifeCycleTest.class, LineDrawingTest.class, ScrollPaneTest.class,
			ManagedTest.class, ManualBindTest.class, MatrixJNITest.class,
			MD5Test.class, MeshMultitextureTest.class, MeshShaderTest.class,
			MeshTest.class, MipMapTest.class, MultitouchTest.class,
			MusicTest.class, MyFirstTriangle.class, ObjTest.class,
			OnscreenKeyboardTest.class, OrthoCamBorderTest.class,
			ParallaxTest.class, ParticleEmitterTest.class, PickingTest.class,
			PixelsPerInchTest.class, PixmapBlendingTest.class,
			PixmapTest.class, PreferencesTest.class,
			ProjectiveTextureTest.class, Pong.class, ProjectTest.class,
			RemoteTest.class, RotationTest.class, ShaderMultitextureTest.class,
			ShadowMappingTest.class, SplineTest.class, SimpleTest.class,
			SimpleAnimationTest.class, SimpleDecalTest.class,
			SimpleStageCullingTest.class, SoundTest.class,
			SpriteCacheTest.class, SpriteCacheOffsetTest.class,
			SpriteBatchRotationTest.class, SpriteBatchShaderTest.class,
			SpriteBatchTest.class, SpritePerformanceTest.class,
			SpritePerformanteTest2.class, StagePerformanceTest.class,
			StageTest.class, TerrainTest.class, TextureDataTest.class,
			TextureDownloadTest.class, TextureFormatTest.class,
			TextureAtlasTest.class, TextInputDialogTest.class,
			TextureRenderTest.class, TiledMapTest.class, TileTest.class,
			UITest.class, VBOVATest.class, VertexArrayTest.class,
			VertexBufferObjectTest.class, VertexArrayClassTest.class,
			VertexBufferObjectClassTest.class,
			VertexBufferObjectShaderTest.class, VibratorTest.class,
			VorbisTest.class, WaterRipples.class, HelloTriangle.class,
			SimpleVertexShader.class, ShapeRendererTest.class,
			MoveSpriteExample.class, StbTrueTypeTest.class, SoundTouchTest.class, Mpg123Test.class, WavTest.class,
			TextButtonTest.class, TextButtonTestGL2.class, TextureBindTest.class, TextureBindTestGL2.class,
			SortedSpriteTest.class, ExternalMusicTest.class, SoftKeyboardTest.class};

	public static String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (Class clazz : tests)
			names.add(clazz.getSimpleName());
		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}

	public static GdxTest newTest(String testName) {
		try {
			Class clazz = Class.forName("com.badlogic.gdx.tests." + testName);
			return (GdxTest) clazz.newInstance();
		} catch (Exception e1) {
			try {
				Class clazz = Class.forName("com.badlogic.gdx.tests.gles2."
						+ testName);
				return (GdxTest) clazz.newInstance();
			} catch (Exception e2) {
				try {
					Class clazz = Class
							.forName("com.badlogic.gdx.tests.examples."
									+ testName);
					return (GdxTest) clazz.newInstance();
				} catch (Exception e3) {
					e3.printStackTrace();
					return null;
				}
			}
		}
	}
}
