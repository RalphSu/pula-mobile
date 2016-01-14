package com.pula.star.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.pula.star.StringUtils;
import com.pula.star.bean.BookingData;
import com.pula.star.bean.CourseData;
import com.pula.star.bean.HuoDongData;
import com.pula.star.bean.JingXuanData;
import com.pula.star.bean.JingxuanDetailData;
import com.pula.star.bean.MyCourseData;
import com.pula.star.bean.MyPoints;
import com.pula.star.bean.MyWorkData;
import com.pula.star.bean.TongGaoData;
import com.pula.star.bean.UserHuoDongData;
import com.pula.star.bean.UserInfo;
import com.pula.star.bean.UserInfoData;

/**
 * @name ClientApi
 * @Descripation 杩欐槸涓�涓敤鏉ヨ闂綉缁滅殑绫�<br>
 *               1銆�<br>
 *               2銆�<br>
 *               3銆�<br>
 * @author 绂规収鍐�
 * @date 2014-10-22
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class ClientApi {
	private static String startId;

	public ClientApi() {
	}

    public static JSONObject ParseJson(final String path, final String encode) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		// HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		// HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpPost httpPost = new HttpPost(path);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						encode);
				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return null;

	}

	/**
	 * @param Url
	 *            涓嬭浇鐨刄rl
	 * @return
	 */
	public static ArrayList<JingXuanData> getJingXuanData(String Url) {
		ArrayList<JingXuanData> list = new ArrayList<JingXuanData>();
		JSONObject json = ParseJson(Url, "utf-8");

		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj").getJSONArray("list");
				
				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->"+i);
					JSONObject data = Data.getJSONObject(i);
					JingXuanData jingXuanData = new JingXuanData();
					jingXuanData.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					jingXuanData.setTitle(element.optString("title"));
					jingXuanData.setPubdate(element.optString("startdate"));
					jingXuanData.setPictureCount(element.optString("cntP"));
					jingXuanData.setImage(element.optString("coverpic"));
					jingXuanData.setViewCount(element.optString("pcolor"));
					jingXuanData.setFavoriteCount(element.getString("likeCnt"));
					jingXuanData.setViewCount(element.optString("viewCnt"));
					jingXuanData.setForeword(element.optString("foreword"));
					UserInfo userInfo = new UserInfo();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					jingXuanData.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					jingXuanData.setDispCities(citys);
					jingXuanData.setCmtCount(element.getString("cntcmt"));
					// System.out.println("----->"+jingXuanData.getDispCities().length);
					/*
					 * JSONArray cmt = element.optJSONArray("cmt"); Comment[]
					 * comments = new Comment[cmt.length()]; if (cmt!=null) {
					 * 
					 * for (int j = 0; j < cmt.length(); j++) { JSONObject
					 * cmtdata = cmt.getJSONObject(i); Comment comment = new
					 * Comment(); UserInfo uInfo = new UserInfo(); JSONObject
					 * user = cmtdata.getJSONObject("user");
					 * uInfo.setAvatar(user.optString("avatar"));
					 * uInfo.setNickname(user.optString("username"));
					 * uInfo.setNickname(user.optString("nickname"));
					 * comment.setUserInfo(uInfo);
					 * comment.setContent(cmtdata.optString("words"));
					 * comment.setLike(cmtdata.optBoolean("isLiked"));
					 * comments[j] = comment; } }
					 * jingXuanData.setComments(comments);
					 */
					jingXuanData.setTourId(element.optString("id"));
					list.add(jingXuanData);
					if (i == Data.length() - 1) {
						startId = jingXuanData.getId();
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("------->"+list.size());
			return list;

		}

	}

	public static String getStartId() {

		return startId;
	}

	/**
	 * 瑙ｆ瀽瀹炰綋鐨凧son鏁版嵁
	 * 
	 * @param tourId
	 * @return
	 */
	public static ArrayList<JingxuanDetailData> getJingxuanDetailDatas(
			String tourId) {
		ArrayList<JingxuanDetailData> list = new ArrayList<JingxuanDetailData>();
		String jingXuanDetailUrl = "http://app.117go.com/demo27/php/formAction.php?submit=getATour2&tourid="
				+ tourId
				+ "&recType=1&refer=PlazaHome&ID2=1&token=3a79c4024f682aee74723a419f6605f9&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
		System.out.println("------>"+jingXuanDetailUrl);
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONObject("obj").getJSONArray(
						"records");
				for (int i = 0; i < Data.length(); i++) {
					JSONObject data = Data.getJSONObject(i);
					JingxuanDetailData jingxuanDetailData = new JingxuanDetailData();
					/*
					 * String location = data.getJSONObject("location")
					 * .optJSONObject("city").optString("city");
					 * jingxuanDetailData.setPoi(location);
					 */
					jingxuanDetailData.setText(data.getString("words"));
					jingxuanDetailData
							.setImage("http://img.117go.com/timg/p308/"
									+ data.getString("picfile"));
					list.add(jingxuanDetailData);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		}

	}
	
	
	
	
	
    public static ArrayList<TongGaoData> getTongGaoDatas() {
		String gonggaoUrl = "http://121.40.151.183:8080/pula-sys/app/image/listDir?sub=gonggao";
		JSONObject json = ParseJson(gonggaoUrl, "utf-8");
		ArrayList<TongGaoData> result = new ArrayList<TongGaoData>();
        if (json == null) {
            return null;
        } else {
            try {
            	
				JSONArray Data = json.getJSONArray("data");			
				for (int i = 0; i < Data.length(); i++) {
				    JSONObject data = Data.getJSONObject(i);
                    TongGaoData tongGaoData = new TongGaoData();
                    tongGaoData.setSub(data.getString("sub"));
                    tongGaoData.setImage(data.getString("fp"));                 
					result.add(tongGaoData);
				}            	                        	
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
	}

    
    
	/**
	 * 瑙ｆ瀽瀹炰綋鐨凧son鏁版嵁
	 * 
	 * @param tourId
	 * @return
	 */
    public static ArrayList<CourseData> getCourseDatas() {
		String courseDetailUrl = "http://121.40.151.183:8080/pula-sys/app/timecourse/list";
		JSONObject json = ParseJson(courseDetailUrl, "utf-8");
		ArrayList<CourseData> result = new ArrayList<CourseData>();
        if (json == null) {
            return null;
        } else {
            try {
            	
				JSONArray Data = json.getJSONArray("records");
				
				for (int i = 0; i < Data.length(); i++) {
				    JSONObject data = Data.getJSONObject(i);
                    CourseData courseData = convertToCourse(data);
					result.add(courseData);
				}            	                        	
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
	}

    private static CourseData convertToCourse(JSONObject data) throws JSONException {
        CourseData courseData = new CourseData();
        courseData.setId(data.getString("id"));
        courseData.setNo(data.getString("no"));
        courseData.setName(data.getString("name"));
        courseData.setBranchName(data.getString("branchName"));
        courseData.setClassRoomName(data.getString("classRoomName"));
        courseData.setDurationMinute(data.getInt("durationMinute"));
        courseData.setEndTime(data.getString("endTime"));
        courseData.setStartTime(data.getString("startTime"));
        courseData.setMaxStudentNum(data.getInt("maxStudentNum"));
        courseData.setPrice(data.getInt("price"));
        courseData.setStartHour(data.getInt("startHour"));
        courseData.setStartMinute(data.getInt("startMinute"));
        
        courseData.setFileId(data.getJSONObject("icon").getString("fileId"));
        courseData.setIconId(data.getJSONObject("icon").getInt("id"));     
        
        courseData.setImage("http://121.40.151.183:8080/pula-sys/app/timecourse/icon?fp="+courseData.getFileId()+"&id="+courseData.getIconId());
        
        return courseData;
    }
	
    public static ArrayList<CourseData> getCouseDetailData(String searchId) {
        ArrayList<CourseData> list = new ArrayList<CourseData>();
        String Url = "http://121.40.151.183:8080/pula-sys/app/timecourse/get?id=" + searchId;
        JSONObject json = ParseJson(Url, "utf-8");
        if (json == null) {
            return null;
        } else {
            try {
                JSONObject data = json.getJSONObject("data");
                CourseData courseData = convertToCourse(data);
                list.add(courseData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    public static ArrayList<HuoDongData> getHuoDongList( ) {
        String jingXuanDetailUrl = "http://121.40.151.183:8080/pula-sys/app/notice/list";
        
        //http://121.40.151.183:8080/pula-sys/app/noticeorder/list?condition.studentNo=PD0DB005
        	
        ArrayList<HuoDongData> list = new ArrayList<HuoDongData>();
        JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
        if (json == null) {
            return null;
        } else {
            try {
                JSONArray Data = json.getJSONArray("records");
                for (int i = 0; i < Data.length(); i++) {
                    HuoDongData huoDongData = new HuoDongData();
                    JSONObject data = Data.getJSONObject(i);
                    huoDongData.setPrice(data.getInt("noticePrice"));
                    huoDongData.setId(data.getString("id"));
                    huoDongData.setName(data.getString("no"));
                    huoDongData.setTitle(data.getString("title"));
                    huoDongData.setContent(data.getString("content"));
                    huoDongData.setUpdateTime(data.getString("updateTime"));
                    huoDongData.setFileId(data.getJSONObject("icon").getString("fileId"));
                    huoDongData.setIconId(data.getJSONObject("icon").getInt("id"));
                    huoDongData.setImage("http://121.40.151.183:8080/pula-sys/app/notice/icon?fp="+huoDongData.getFileId()+"&id="+huoDongData.getIconId());                   
                    huoDongData.setUrlS("http://121.40.151.183:8080/pula-sys/app/notice/appshow?id=" + huoDongData.getId());
                    
                    list.add(huoDongData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    
    public static ArrayList<UserHuoDongData> getUserHuoDongList(String studentNo ) {
    	
               
        String UserHuoDongUrl = "http://121.40.151.183:8080/pula-sys/app/noticeorder/list?condition.studentNo="+studentNo;
        	
        ArrayList<UserHuoDongData> list = new ArrayList<UserHuoDongData>();
        
        JSONObject json = ParseJson(UserHuoDongUrl, "utf-8");
        
        if (json == null) {
            return null;
        } else {
            try {
                JSONArray Data = json.getJSONArray("records");
                
                for (int i = 0; i < Data.length(); i++) {
                    UserHuoDongData huoDongData = new UserHuoDongData();
                    JSONObject data = Data.getJSONObject(i);
                    huoDongData.setBookingCount(data.getInt("count"));
                    huoDongData.setCreateTime(data.getString("createTime"));
                    huoDongData.setPrice(data.getJSONObject("notice").getInt("noticePrice"));
                    huoDongData.setId(data.getJSONObject("notice").getString("id"));
                    huoDongData.setName(data.getJSONObject("notice").getString("no"));
                    huoDongData.setTitle(data.getJSONObject("notice").getString("title"));
                    huoDongData.setContent(data.getJSONObject("notice").getString("content"));
                    huoDongData.setUpdateTime(data.getJSONObject("notice").getString("updateTime"));
                    huoDongData.setFileId(data.getJSONObject("notice").getJSONObject("icon").getString("fileId"));
                    huoDongData.setIconId(data.getJSONObject("notice").getJSONObject("icon").getInt("id"));
                    // FIXME: use real img path
                    //huoDongData.setImage("http://121.40.151.183:8080/pula-sys/app/image/icon?fp=" + "logo.png" /* data.getString("imgPath") */ 
                    //        + "&sub=notice");
                    
                    huoDongData.setImageUrl("http://121.40.151.183:8080/pula-sys/app/notice/icon?fp="+huoDongData.getFileId()+"&id="+huoDongData.getIconId());
                    
                    huoDongData.setUrlS("http://121.40.151.183:8080/pula-sys/app/notice/appshow?id=" + huoDongData.getId());
                    
                    list.add(huoDongData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    
    
    
    
    
    
	public static boolean getLoginStatus(String username, String password) {
		String loginUrl = "http://121.40.151.183:8080/pula-sys/app/studentinterface/login?loginId="
				+ username + "&password=" + password;
		
		boolean result = false;
		
		JSONObject json = ParseJson(loginUrl, "utf-8");
		
		if (json == null) {
			return false;
		} else {
			try {
				
			  result = json.getBoolean("error");
			  	
			} catch (Exception e) {
				e.printStackTrace();
}
		}
		return !result;
	}

	public static UserInfoData getUserInfoData(String username, String password) {
		String getInfoUrl = "http://121.40.151.183:8080/pula-sys/app/studentinterface/info?loginId="
				+ username + "&password=" + password;

		UserInfoData userInfo = new UserInfoData();
		Boolean result = false;
		
		JSONObject json = ParseJson(getInfoUrl, "utf-8");
		if (json == null) {
	
			return null;
		} else {
			try {
			
				result = json.getBoolean("error");
				if (result == false) {
					System.out.println("result != null");
					JSONObject data = json.getJSONObject("data");

					userInfo.setName(data.getString("name"));
					userInfo.setAddress(data.optString("address"));
					userInfo.setId(data.getInt("id"));
					userInfo.setEnabled(data.getBoolean("enabled"));
					userInfo.setNo(data.getString("no"));
					userInfo.setParentName(data.optString("parentName"));
					userInfo.setParentCaption(data.optString("parentCaption"));
					userInfo.setGender(data.getInt("gender"));
					userInfo.setGenderName(data.optString("genderName"));
					userInfo.setPoints(data.getInt("points"));
					userInfo.setMobile(data.optString("mobile"));
				
					userInfo.setBirthday(data.optLong("birthday"));
					userInfo.setPhone(data.optInt("phone"));
					userInfo.setZip(data.optInt("zip"));
					userInfo.setBarCode(data.optString("barcode"));

				}
				
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}

	
	public static  MyCourseData getMyCourseData(String studentNo){
		String getMyCourseDataUrl = "http://121.40.151.183:8080/pula-sys/app/timecourseorder/list?condition.studentNo=" + studentNo;
		
		MyCourseData courseData = new MyCourseData();
		
		JSONObject json =  ParseJson(getMyCourseDataUrl, "utf-8");
		
		if(json != null){
		  try{
			  Log.i("ClientAPI.getMyCourseData", ""+ json);
			  JSONArray records = json.getJSONArray("records");
			  if(records != null){
				  if(records.length() >= 1)
				  { 
					 JSONObject data = records.getJSONObject(0);
					 
					 courseData.setCreateTime(data.getString("createTime"));
					 courseData.setCourseTime(data.getString("courseTime"));
					 courseData.setUpdateTime(data.getString("updateTime"));
					 courseData.setLevel(data.getInt("level"));
					 courseData.setPaidCount(data.getInt("paiedCount"));
					 courseData.setUsedCount(data.getInt("usedCount"));
					 courseData.setSpecialCourseCount(data.getInt("specialCourseCount"));
					 courseData.setUsedSpecialCourseCount(data.getInt("usedSpecialCourseCount"));
					 courseData.setHuodongCount(data.getInt("huodongCount"));
					 courseData.setUsedHuodongCount(data.getInt("usedHuodongCount"));
					 courseData.setGongfangCount(data.getInt("gongfangCount"));
					 courseData.setUsedGongFangCount(data.getInt("usedGongFangCount"));				 				 
				  }
			  }
			  
		  }
		  catch (Exception e) {
              Log.e("ClientAPI.getMyCourseData", "Fail to get my course data !", e);
          }
		}
		
		return courseData;
	}
	
    public static List<BookingData> getBookingList(String studentNo, String closeStatus) {
    	
        if (StringUtils.isEmpty(studentNo)) {
            return Collections.emptyList();
        }
        
        String getBookingUrlTemp = "http://121.40.151.183:8080/pula-sys/app/audition/list?condition.closedStatus=%s&condition.studentNo=%s";
 
        
        String url = String.format(getBookingUrlTemp, closeStatus, studentNo);

        List<BookingData> bookingInfoList = new ArrayList<BookingData>();

        JSONObject json = ParseJson(url, "utf-8");
        if (json != null) {
            try {
                JSONArray records = json.getJSONArray("records");
                if (records != null) {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject data = records.getJSONObject(i);
                        BookingData bookingInfo = new BookingData();
                        bookingInfo.setParentName(data.getString("parent"));
                        bookingInfo.setComments(data.optString("comments"));
                        bookingInfo.setId(data.getInt("id"));
                        bookingInfo.setStudentName(data.optString("student"));
                        bookingInfo.setContent(data.getString("content"));
                        bookingInfo.setResultName(data.optString("resultName"));
                        bookingInfo.setResultId(data.optString("resultId"));
                        bookingInfo.setOwnerName(data.optString("ownerName"));
                        bookingInfo.setCreatedTime(new Date(data.getLong("createdTime")));
                        bookingInfo.setPhone(data.optString("phone"));
                        bookingInfo.setAge(data.optString("age"));
                        bookingInfo.setBranchName(data.optString("branchName"));
                        bookingInfo.setPlan1(data.optString("plan1"));
                        bookingInfo.setPlan2(data.optString("plan2"));
                        bookingInfo.setPlan3(data.optString("plan3"));
                        bookingInfo.setPlan4(data.optString("plan4"));
                        bookingInfo.setPlan5(data.optString("plan5"));

                        bookingInfoList.add(bookingInfo);
                    }
                }
            } catch (Exception e) {
                Log.e("ClientAPI.getBookingList", "Fail to get booking list!", e);
            }
        }
        return bookingInfoList;
    }
	
    
  public static List<MyPoints> getMyPointList(String studentNo) {
    	
        if (StringUtils.isEmpty(studentNo)) {
            return Collections.emptyList();
        }
        
        String getPointUrlTemp = "http://121.40.151.183:8080/pula-sys/app/studentpoints/list?condition.loginId=%s&_json=1";
     
        String url = String.format(getPointUrlTemp,studentNo);
        
        //String url = String.format(getPointUrlTemp,"JQ00008");
        
        //String url = "http://121.40.151.183:8080/pula-sys/app/studentpoints/list?&_json=1";
        
        
        List<MyPoints> myPointInfoList = new ArrayList<MyPoints>();

        JSONObject json = ParseJson(url, "utf-8");
        if (json != null) {
            try {
            	
            	 Log.e("total records", ""+ json);
            	  
                JSONArray records = json.getJSONArray("records");
                if (records != null) {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject data = records.getJSONObject(i);
                        MyPoints myPointInfo = new MyPoints();            
                        myPointInfo.setOwnerNo(data.optString("ownerNo"));
                        myPointInfo.setOwnerName(data.optString("ownerName"));
                        myPointInfo.setCreatedTime(data.optString("createdTime"));
                        myPointInfo.setPoints(data.getString("points"));
                        myPointInfo.setType(data.getString("type"));
                        myPointInfo.setComments(data.optString("comments"));
                        myPointInfoList.add(myPointInfo);
                    }
                }
            } catch (Exception e) {
                Log.e("ClientAPI.getMyPointList", "Fail to get point list!", e);
            }
        }
        return myPointInfoList;
    }
  
  public static List<MyWorkData> getMyWorkList(String studentNo) {
  	
      if (StringUtils.isEmpty(studentNo)) {
          return Collections.emptyList();
      }
      
      String getPointUrlTemp = "http://121.40.151.183:8080/pula-sys/app/timecoursework/list?condition.studentNo=%s&_json=1";
   
      String url = String.format(getPointUrlTemp,studentNo);
      
      List<MyWorkData> myWorkDataInfoList = new ArrayList<MyWorkData>();

      JSONObject json = ParseJson(url, "utf-8");
      if (json != null) {
          try {
          	      	  
              JSONArray records = json.getJSONArray("records");
              if (records != null) {
                  for (int i = 0; i < records.length(); i++) {
                      JSONObject data = records.getJSONObject(i);
                      MyWorkData myWorkDataInfo = new MyWorkData();  
                      
                      myWorkDataInfo.setWorkEffectDate(data.optString("workEffectDate"));
                      myWorkDataInfo.setId(data.optInt("id"));
                      myWorkDataInfo.setRate(data.optInt("rate"));
                      myWorkDataInfo.setNo(data.optString("no"));
                      myWorkDataInfo.setCourseNo(data.optString("courseNo"));
                      myWorkDataInfo.setBranchNo(data.optString("branchNo"));
                      myWorkDataInfo.setComments(data.optString("comments"));
                      myWorkDataInfo.setIconId(data.getJSONObject("icon").optInt("id"));                     
                      myWorkDataInfo.setFileId(data.getJSONObject("icon").optString("fileId"));
                      myWorkDataInfo.setFileName(data.getJSONObject("icon").optString("name"));
                                                               
                      myWorkDataInfoList.add(myWorkDataInfo);
                  }
              }
          } catch (Exception e) {
              Log.e("ClientAPI.getMyWorkList", "Fail to get work list!", e);
          }
      }
      return myWorkDataInfoList;
  }
    
	/**
	 * 通过 url 联网得到返回字符串
	 * 1.建立连接
	 * 2.讲服务端的内容获取到buffer缓冲区中
	 * 3.讲内容从buffer中拿出来，转换为string，作为函数的返回值
	 * ------json解析
	 * ------Gson
	 */
	public static String getDataByUrl(String path){
		HttpURLConnection httpcon=null;
		InputStream inputS=null;
		BufferedReader b=null;
		//解析流的时候需要以下两个对象
		String line="";
		StringBuilder sb=new StringBuilder();
		try {
			//准备url，把path--->url
			URL url=new URL(path);
			//1.建立连接,通过HttpURLConnection打开连接
			httpcon=(HttpURLConnection) url.openConnection();
			if (httpcon.getResponseCode() == 200) {
				//2.将服务端中的内容获取到buffer缓冲区中
				inputS=httpcon.getInputStream();
				//把数据流放入缓冲区，并得到一个输出流
				b = new BufferedReader(new InputStreamReader(inputS));
				//3.将内容从缓冲区拿出来
				while ((line=b.readLine())!=null) {
					sb.append(line);
				}
				return sb.toString();
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}finally{
			try {
				if(httpcon!=null){
					httpcon.disconnect();
				}
				if(b!=null){
					b.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
    public static boolean audition_create(String name,String parentName,String age, String phone, String branchNo, String plan, String content){

		//String audition_create_url = "http://121.40.151.183:8080/pula-sys/app/audition/_update?student=ff&id=&age=13&phone=165666"
		//		+ "&parent=ff&content=f&plan1=f&plan2=f&plan3=f&plan4=f&plan5=f&resultId=&comments=f&id=325&_json=1";
		
		
		String audition_create_url="http://121.40.151.183:8080/pula-sys/app/audition/create?audition.parent="+ parentName + "&audition.comments="+content+"&audition.student="
				+name+"&audition.branchNo="+branchNo+"&audition.branchName="+"&audition.plan1="+ plan + "&audition.age="+ age + "&audition.phone="+phone +"&_json=1";
					
		Log.i("audition_create_url", audition_create_url);
		boolean result = false;
		
		JSONObject json = ParseJson(audition_create_url, "utf-8");
		
		Log.i("json", ""+json);
		if (json == null) {
			return false;
		} else {
			try {
			  	
			  result = json.getBoolean("error");
			  	
			} catch (Exception e) {
				e.printStackTrace();
}
		}
		return !result;
	
    	
    }
    
    
    public static boolean resetPwd(String mobile){
    	
    	String reset_pwd_url="http://121.40.151.183:8080/pula-sys/app/studentinterface/resetPassword?mobile=" + mobile;
    	
    	
    	boolean result = false;
    	
    	JSONObject json = ParseJson(reset_pwd_url,"utf-8");
    	
    	if(json == null){
    		return false;
    	}
    	else
    	{
    		try{
    			result = json.getBoolean("error");
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    	}
    	return !result;
    }
    
    public static boolean changePwd(String studentNo,String oldPwd,String newPwd){
    	
    	String change_pwd_url = "http://121.40.151.183:8080/pula-sys/app/studentinterface/updatePassword?studentNo="+studentNo+"&oldPassword="+oldPwd
    			+ "&newPassword="+newPwd+"&_json=1";
    	
        boolean result = false;
    	
    	JSONObject json = ParseJson(change_pwd_url,"utf-8");
    	
    	if(json == null){
    		return false;
    	}
    	else
    	{
    		try{
    			result = json.getBoolean("error");
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    	}
    	return !result;
    }
}	
	
