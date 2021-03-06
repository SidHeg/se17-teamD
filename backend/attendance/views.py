from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader
from django.utils import timezone
from .models import professor, student, course, attendance
import json, sys, ast

def login(post):
	print("inside login function")
	try:
		user_id = post['user_id'].strip().lower()
		password = post['password'].strip().lower()
	except exception, e:
		print("username or password not received: %s" %e)
		return "failed"
	try:
		s1 =  student.objects.get(stud_id=user_id)
		if s1.password == password:
			print("student login successful")
			return "student"
		else:
			print("student login failed")
			return "failed"
	except:
		print("not a student, checking for professor")
	try:
		p1 =  professor.objects.get(prof_id=user_id)
		if p1.password == password:
			print("professor login successful")
			return "professor"
		else:
			print("professor login failed")
			return "failed"
	except exception, e:
		print("both student and professor login failed: %s" %e)
		return "failed"


def checkGPS(lat1, lon1, lat2, lon2):
	if((lat1 - lat2)**2 +(lon1 - lon2)**2 < 0.000010):
		return True
	return False


def gps(post):
	print("inside gps function")
	try:
		user_id = post['user_id'].strip().lower()
		course_id = post['course_id'].strip().lower()
		latitude = post['latitude'].strip().lower()
		longitude = post['longitude'].strip().lower()
	except exception, e:
		print("gps_not_received: %s" %e)
		return "failed"

	log = login(post)

	if log == "professor":
		try:
			c1 = course.objects.filter(course_id=course_id, professor__prof_id=user_id)[0]
			c1.longitude = longitude
			print("step1")
			c1.latitude = latitude
			print("step2")
			c1.save()
			print("professor_gps_stored_sucessfully")
			return "successful"
		except exception, e:
			print("unable to save professor gps coordinates: %s" %e)
			return "failed"

	elif log == "student":
		try:
			c1 = course.objects.filter(course_id=course_id)[0]
			s1 = student.objects.get(stud_id = user_id)
			lat1 = float(c1.latitude)
			lat2 = float(latitude)
			lon1 = float(c1.longitude)
			lon2 = float(longitude)
			print("converted coordinates from unicode to float")
			if checkGPS(lat1, lon1, lat2, lon2):
				a1 = attendance.objects.create(course = c1, student=s1)
				a1.gps = timezone.now()
				a1.save()
				print("student_gps_attendance_successful")
				return "successful"
			else:
				print("student_gps_attendance_failed")
				return "failed"
		except exception, e:
			print("unable to save student gps coordinates: %s" %e)

	else:
		print("login failed, hence gps failed")
		return log


def qrc(post):
	print("inside qrc function")
	try:
		user_id = post['user_id'].strip().lower()
		text = post['text'].strip().lower()
		image = post['image']
		course_id = post['course_id'].strip().lower()
	except exception, e:
		print("qrc not received: %s" %e)
		return "failed"

	log = login(post)

	if log == "professor":
		c1 = course.objects.filter(course_id=course_id)[0]
		c1.qrcode = text
		c1.qrimage = image
		c1.qrtime = timezone.now()
		c1.save()
		return "successful"

	elif log == "student":
		c1 = course.objects.filter(course_id=course_id)[0]
		if c1.qrcode == text:
			s1 = student.objects.get(stud_id = user_id)
			a1 = attendance.objects.create(course = c1, student=s1)
			a1.qrcode = timezone.now()
			a1.save()
			return "successful"
		else:
			return "failed"
	else:
		return log


def nfc(post):
	print("inside nfc function")
	try:
		user_id = post['user_id'].strip().lower()
		text = post['text'].strip().lower()
		course_id = post['course_id'].strip().lower()
	except:
		print("exception")
		return "failed"
	print("nfc_text: "+text)
	log = login(post)

	if log == "professor":
		print("nfc login professor")
		c1 = course.objects.filter(course_id=course_id)[0]
		c1.nfc = text
		c1.save()
		print("nfc professor saved")
		return "successful"

	elif log == "student":
		c1 = course.objects.filter(course_id=course_id)[0]
		if c1.nfc == text:
			print("inside nfc student")
			s1 = student.objects.get(stud_id = user_id)
			a1 = attendance.objects.create(course = c1, student=s1)
			a1.nfc = timezone.now()
			a1.save()
			print("nfc student saved")
			return "successful"
		else:
			print("nfc student failed")
			return "failed"
	else:
		return log
	

def index(request):
	try:
		print("inside index")
		if request.method == 'POST':
			received_data=json.loads(request.body)
			print("converted json to dictionary")
			try:
				status = received_data["status"].strip().lower()
				#return HttpResponse(status)
			except:
				return HttpResponse("status_unavailable %s" %received_data)
			try:
				if status == r'login':
					return HttpResponse(login(received_data))
				elif status == r'gps':
					return HttpResponse(gps(received_data))
				elif status == r'qrc':
					return HttpResponse(qrc(received_data))
				elif status == r'nfc':
					return HttpResponse(nfc(received_data))
				else:
					return HttpResponse("wrong_status")
			except:
				print(sys.exc_info()[0])
				return HttpResponse(sys.exc_info()[0])
		else:
			return HttpResponse("In a post method send a json data")
	except exception, e:
		print("unable to convert json to dictionary %s" %e)
		return HttpResponse("unable to convert json to dictionary %s" %sys.exc_info()[0])

def qrimage(request):
	c1 = course.objects.filter(course_id="csc510")[0]
	return HttpResponse("<img src=\"%s\">" %c1.qrimage)