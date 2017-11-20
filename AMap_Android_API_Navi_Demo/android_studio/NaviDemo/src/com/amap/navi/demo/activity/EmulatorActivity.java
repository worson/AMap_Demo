package com.amap.navi.demo.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.navi.demo.R;


public class EmulatorActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_basic_navi);
		mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
		mAMapNaviView.onCreate(savedInstanceState);
		mAMapNaviView.setAMapNaviViewListener(this);

		mAMapNaviView.setLockTilt(0);

	}

	@Override
	public void onInitNaviSuccess() {
		super.onInitNaviSuccess();
		/**
		 * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
		 * 
		 * @congestion 躲避拥堵
		 * @avoidhightspeed 不走高速
		 * @cost 避免收费
		 * @hightspeed 高速优先
		 * @multipleroute 多路径
		 *
		 *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。 
		 *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
		 */
		int strategy = 0;
		try {
			//再次强调，最后一个参数为true时代表多路径，否则代表单路径
			strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

	}

	@Override
	public void onCalculateRouteSuccess() {
		super.onCalculateRouteSuccess();
		mAMapNavi.startNavi(NaviType.EMULATOR);
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo naviinfo) {
		super.onNaviInfoUpdate(naviinfo);

	}

	@Override
	public void onLocationChange(AMapNaviLocation location) {
		super.onLocationChange(location);
		Log.e(TAG,"location changed");
		Log.e("onLocationChange",String.format("gpsframe,time,%s,lat,%s,lon,%s,accuracy,%s,currentTime ,%s,elapsedRealtime ,%s," +
						"Altitude ,%s,Bearing,%s,Speed,%s",
				location.getTime(),location.getCoord().getLatitude(),location.getCoord().getLongitude(),
				location.getAccuracy(),location.getCoord(), SystemClock.elapsedRealtimeNanos(),location.getBearing(),
				location.getAltitude(),location.getBearing(),location.getSpeed()));
	}
}
