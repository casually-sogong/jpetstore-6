<%@ taglib prefix="stri" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%--

       Copyright 2010-2022 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          https://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../common/IncludeTop.jsp"%>

<jsp:useBean id="catalog"
	class="org.mybatis.jpetstore.web.actions.CatalogActionBean" />

<div id="BackLink"><stripes:link
	beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean"
	event="viewCategory">
	<stripes:param name="categoryId"
		value="${actionBean.product.categoryId}" />
	Return to ${actionBean.product.categoryId}
</stripes:link></div><br><br>

<div id="Catalog">

<h2>${actionBean.product.name}</h2>

<table>
	<tr>
		<th>Item ID</th>
		<th>Product ID</th>
		<th>Description</th>
		<th>List Price</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${actionBean.itemList}">
		<tr>
			<td><stripes:link
				beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean"
				event="viewItem">
				<stripes:param name="itemId" value="${item.itemId}" />
				${item.itemId}
			</stripes:link></td>
			<td>${item.product.productId}</td>
			<td>${item.attribute1} ${item.attribute2} ${item.attribute3}
			${item.attribute4} ${item.attribute5} ${actionBean.product.name}</td>
			<td><fmt:formatNumber value="${item.listPrice}"
				pattern="$#,##0.00" /></td>
			<td><stripes:link class="Button"
				beanclass="org.mybatis.jpetstore.web.actions.CartActionBean"
				event="addItemToCart">
				<stripes:param name="workingItemId" value="${item.itemId}" />
        	Add to Cart
        </stripes:link></td>
		</tr>
	</c:forEach>
</table>
	<c:if test="${not empty actionBean.reviewList}">
		<h2>분양 후기</h2>
		<table>
			<tr>
				<th>Review ID</th>
				<th>Title</th>
				<th>Writer</th>
				<th>Date</th>
			</tr>
			<c:forEach var="review" items="${actionBean.reviewList}">
				<tr>
					<td><stripes:link
							beanclass="org.mybatis.jpetstore.web.actions.ReviewActionBean"
							event="viewReview">
						<stripes:param name="reviewId" value="${review.reviewId}"/>
						${review.reviewId}
					</stripes:link></td>
					<td>${review.title}</td>
					<td>${review.userId}</td>
					<td><fmt:formatDate
							value="${review.createdAt}" pattern="yyyy/MM/dd hh:mm:ss" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>





