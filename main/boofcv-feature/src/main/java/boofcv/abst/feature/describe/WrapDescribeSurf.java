/*
 * Copyright (c) 2011-2020, Peter Abeles. All Rights Reserved.
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

package boofcv.abst.feature.describe;

import boofcv.alg.feature.describe.DescribePointSurf;
import boofcv.alg.transform.ii.GIntegralImageOps;
import boofcv.struct.feature.TupleDesc_F64;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;


/**
 * @author Peter Abeles
 */
public class WrapDescribeSurf<T extends ImageGray<T>, II extends ImageGray<II>>
		implements DescribeRegionPoint<T, TupleDesc_F64> {

	// computes SURF feature descriptor
	DescribePointSurf<II> surf;
	// integral image
	II ii;

	ImageType<T> imageType;
	final double canonicalRadius;

	public WrapDescribeSurf(DescribePointSurf<II> surf , Class<T> imageType)
	{
		this.surf = surf;
		this.imageType = ImageType.single(imageType);
		this.canonicalRadius = surf.getCanonicalWidth()/2.0;
	}

	@Override
	public TupleDesc_F64 createDescription() {
		return new TupleDesc_F64(surf.getDescriptionLength());
	}

	@Override
	public void setImage(T image) {
		if( ii != null ) {
			ii.reshape(image.width,image.height);
		}

		// compute integral image
		ii = GIntegralImageOps.transform(image,ii);
		surf.setImage(ii);
	}

	@Override
	public boolean process(double x, double y, double orientation , double radius, TupleDesc_F64 storage) {
		double scale = radius/canonicalRadius;
		surf.describe(x,y, orientation, scale, true, storage);
		return true;
	}

	@Override
	public boolean isScalable() {
		return true;
	}

	@Override
	public boolean isOriented() {
		return true;
	}

	@Override
	public ImageType<T> getImageType() {
		return imageType;
	}

	@Override
	public double getCanonicalWidth() {
		return surf.getCanonicalWidth();
	}

	@Override
	public Class<TupleDesc_F64> getDescriptionType() {
		return TupleDesc_F64.class;
	}
}
