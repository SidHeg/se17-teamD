from django.conf.urls import url
from . import views

app_name = 'attendance'
urlpatterns = [
    url(r'^$', views.index, name='index'),
    #url(r'^$',views.gps, name='status'),
]