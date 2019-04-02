<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<tags:template>
	<jsp:attribute name="head">  
  	</jsp:attribute>
	<jsp:body>
		<div class="heading">
			Here is a random CryptedArray encrypted with ${ key } AES key
		</div>
		<div class="array">
			<c:forEach begin="0" end="${width -1}" varStatus="line">
				<div class="line">
					<c:forEach begin="0" end="${height - 1}" varStatus="col">
						<c:if test="${(line.index + col.index)%2 == 0}">
							<div class="double color1">
								${ first[line.index][col.index] }
							</div>
						</c:if>
						<c:if test="${(line.index + col.index)%2 != 0}">
							<div class="double color2">
								${ first[line.index][col.index] }
							</div>
						</c:if>
						
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<div class="actions">
			<a href="/project-app/?cryptedArrayId=0">
				<button>
					Generate new random array
				</button>
			</a>
			<a href="/project-app/?cryptedArrayId=${arrayId}&action=encrypt">
				<button>
					Encrypt the array
				</button>
			</a>
			<a href="/project-app/?cryptedArrayId=${arrayId}&action=decrypt">
				<button>
					Decrypt the array
				</button>
			</a>
		</div>
	</jsp:body>
</tags:template>