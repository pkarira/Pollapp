from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^(?P<question_id>[0-9]+)/$', views.detail, name='detail'),
    url(r'^results/$', views.totalResults, name='results'),
    url(r'^(?P<question_id>[0-9]+)/singleresults/$', views.singleResult, name='results'),
    url(r'^vote$', views.vote, name='vote'),
    url(r'^vote$', views.vote, name='vote'),
]
