/*
 * Copyright (c) 2011-2017, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
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
 */

package boofcv.alg.geo.calibration;

import boofcv.struct.calib.CameraModel;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;
import org.ejml.data.DMatrixRMaj;

/**
 * Interface that specifies how to optimize a intrinsic camera model
 *
 * @author Peter Abeles
 */
public interface Zhang99IntrinsicParam {

	/**
	 * Set from the initial estimate
	 *
	 * @param K 3x3 intrinsic matrix
	 * @param radial Radial distortion parameters
	 */
	void initialize(DMatrixRMaj K , double[] radial);

	int numParameters();
	int setFromParam( double param[] );
	int convertToParam( double param[] );

	<T extends CameraModel>T getCameraModel();

	Zhang99IntrinsicParam createLike();

	void setTo( Zhang99IntrinsicParam orig );

	/**
	 * Projects a point on the camera
	 *
	 * @param cameraPt (Input) 3D point in camera reference frame. Can be modified.
	 * @param pixel (Output) Projected pixel coordinate of point
	 */
	void project(Point3D_F64 cameraPt , Point2D_F64 pixel );
}
